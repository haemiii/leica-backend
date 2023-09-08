package com.example.leica_refactoring.post;

import com.example.leica_refactoring.dto.RequestPostDto;
import com.example.leica_refactoring.dto.ResponsePostDto;
import com.example.leica_refactoring.entity.Member;
import com.example.leica_refactoring.entity.Post;
import com.example.leica_refactoring.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    // 게시물 생성
    public Long save(RequestPostDto requestPostDto, String memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        if(member == null){
            throw new UsernameNotFoundException("존재하는 사용자가 없습니다.");
        }else{
            Post post = Post.builder()
                    .title(requestPostDto.getTitle())
                    .content(requestPostDto.getContent())
                    .thumbnail(requestPostDto.getThumbnail())
                    .member(member)
                    .build();

            Post save = postRepository.save(post);
            return save.getId();
        }
    }

    public List<ResponsePostDto> findAll() {
        List<Post> all = postRepository.findAll();

        List<ResponsePostDto> collect = all.stream().map(post ->
                ResponsePostDto.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .thumbnail(post.getThumbnail())
                        .writer(post.getMember().getMemberId())
                        .build()
        ).collect(Collectors.toList());

        return collect;
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
}
