package com.example.leica_refactoring.post;

import com.example.leica_refactoring.dto.RequestPostDto;
import com.example.leica_refactoring.dto.ResponsePostDto;
import com.example.leica_refactoring.dto.ResponsePostListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    // 전체 게시물 조회
    @GetMapping("/")
    public ResponsePostListDto post(){
        ResponsePostListDto all = postService.findAll();

        return all;
    }

    // 카테고리별 게시물 조회(부모 카테고리 기준)
    @GetMapping("/find/post/{parentCategory}")
    public ResponsePostListDto findAllPostByParentCategory(@PathVariable String parentCategory){
        ResponsePostListDto allPostByParentCategory = postService.findAllPostByParentCategory(parentCategory);
        return allPostByParentCategory;
    }

    // 카테고리별 게시물 조회(자식 카테고리 기준)
    @GetMapping("/find/post/{parentCategory}/{childCategory}")
    public ResponsePostListDto findAllPostByChildCategory(@PathVariable String parentCategory, @PathVariable String childCategory){
        ResponsePostListDto allPostByChildCategory = postService.findAllPostByChildCategory(parentCategory, childCategory);
        return allPostByChildCategory;
    }




    // 게시물 생성(ADMIN만 가능)
    @PostMapping("/post")
    public Long createPost(@RequestBody RequestPostDto requestPostDto, @AuthenticationPrincipal UserDetails userDetails){

        Long save = postService.save(requestPostDto, userDetails.getUsername());

        return save;
    }

    @GetMapping("/post/{id}")
    public ResponsePostDto createPost(@PathVariable Long id){

        ResponsePostDto save = postService.showPost(id);

        return save;
    }

    // 자기 자신만 가능!
    @PutMapping("/post/{id}")
    public Long updatePost(@RequestBody RequestPostDto requestPostDto, @PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails){

        Long update = postService.update(id, requestPostDto, userDetails.getUsername());
        return update;
    }

    // 자기 자신만 가능

    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        postService.delete(id, userDetails.getUsername());
    }

}
