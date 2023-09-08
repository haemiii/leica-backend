package com.example.leica_refactoring.post;

import com.example.leica_refactoring.category.CategoryRepository;
import com.example.leica_refactoring.dto.RequestPostDto;
import com.example.leica_refactoring.dto.ResponsePostDto;
import com.example.leica_refactoring.dto.ResponsePostListDto;
import com.example.leica_refactoring.entity.Category;
import com.example.leica_refactoring.entity.Member;
import com.example.leica_refactoring.entity.Post;
import com.example.leica_refactoring.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;


    // 게시물 생성
    public Long save(RequestPostDto requestPostDto, String memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        if(member == null){
            throw new UsernameNotFoundException("존재하는 사용자가 없습니다.");
        }else{
            Category category = categoryRepository.findByName(requestPostDto.getParentName());

            Category childCategory = null;

            for (Category category1 : category.getChild()) {
                if (category1.getName().equals(requestPostDto.getChildName())) {
                    childCategory = category1;
                    break;
                }
            }

            Post post = Post.builder()
                    .title(requestPostDto.getTitle())
                    .content(requestPostDto.getContent())
                    .thumbnail(requestPostDto.getThumbnail())
                    .childCategory(childCategory)
                    .member(member)
                    .build();

            Post save = postRepository.save(post);
            return save.getId();
        }
    }

    public ResponsePostListDto findAll() {
        List<Post> all = postRepository.findAll();
        int size = all.size();

        List<ResponsePostDto> collect = all.stream().map(post ->
                ResponsePostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .thumbnail(post.getThumbnail())
                        .writer(post.getMember().getMemberId())
                        .category(post.getChildCategory().getName())
                        .build()
        ).collect(Collectors.toList());

        ResponsePostListDto build = ResponsePostListDto.builder()
                .size((long) size)
                .childList(collect)
                .build();

        return build;
    }

    public ResponsePostListDto findAllPostByParentCategory(String parentName) {
        Category category = categoryRepository.findByName(parentName);
        if(category == null){
            return ResponsePostListDto.builder()
                    .size(0L) // 게시물 수를 0으로 설정
                    .childList(Collections.emptyList()) // 빈 리스트 설정
                    .build();
        }else{
            Long totalPostCount = (long) category.getChild().stream()
                    .flatMap(child -> child.getPosts().stream()).mapToInt(post -> 1).sum(); // 각 게시물에 대해 1을 더함

            List<ResponsePostDto> postDtos = category.getChild().stream()
                    .flatMap(child -> child.getPosts().stream()
                            .map(post -> ResponsePostDto.builder()
                                    .id(post.getId())
                                    .title(post.getTitle())
                                    .content(post.getContent())
                                    .thumbnail(post.getThumbnail())
                                    .writer(post.getMember().getMemberId())
                                    .category(post.getChildCategory().getName())
                                    .build()
                            )
                    )
                    .collect(Collectors.toList());

            ResponsePostListDto build = ResponsePostListDto.builder()
                    .size(totalPostCount)
                    .childList(postDtos)
                    .build();

            return build;
        }
    }

    public ResponsePostListDto findAllPostByChildCategory(String parentName, String childName) {
        List<Category> childCategories = categoryRepository.findAllByName(childName);

        Category selectedChildCategory = null;
        for (Category category : childCategories) {
            if (category.getParent().getName().equals(parentName)) {
                selectedChildCategory = category;
                break;
            }
        }

        if (selectedChildCategory == null) {
            return ResponsePostListDto.builder()
                    .size(0L)
                    .childList(Collections.emptyList())
                    .build();
        } else {
            Long totalPostCount = (long) selectedChildCategory.getPosts().size();

            List<ResponsePostDto> postDtos = selectedChildCategory.getPosts().stream()
                    .map(post -> ResponsePostDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .thumbnail(post.getThumbnail())
                            .writer(post.getMember().getMemberId())
                            .category(post.getChildCategory().getName())
                            .build()
                    )
                    .collect(Collectors.toList());

            return ResponsePostListDto.builder()
                    .size(totalPostCount)
                    .childList(postDtos)
                    .build();
        }
    }



    public Long update(Long id, RequestPostDto requestPostDto, String username) {
        Member member = memberRepository.findByMemberId(username);
        Optional<Post> post = postRepository.findById(id);
        if (member == null){
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");

        }if(!Objects.equals(post.get().getMember().getMemberId(), username)){
            throw new AuthorOnlyAccessException();
        }
        Post originPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));

        // 게시물 내용 업데이트
        originPost.setTitle(requestPostDto.getTitle());
        originPost.setContent(requestPostDto.getContent());
        originPost.setThumbnail(requestPostDto.getThumbnail());
        originPost.setMember(member);

        // 업데이트된 게시물 저장
        Post updatedPost = postRepository.save(originPost);
        return updatedPost.getId();

    }

    public void delete(Long id, String username) {
        Member member = memberRepository.findByMemberId(username);
        Optional<Post> post = postRepository.findById(id);

        if (member == null){
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }if(!Objects.equals(post.get().getMember().getMemberId(), username)){
            throw new AuthorOnlyAccessException();
        }
        postRepository.deleteById(id);

    }


    public ResponsePostDto showPost(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = postOptional.orElseThrow(() -> new NoSuchElementException("게시물이 존재하지 않습니다."));

        ResponsePostDto build = ResponsePostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .writer(post.getMember().getMemberId())
                .category(post.getChildCategory().getName())
                .build();

        return build;
    }
}
