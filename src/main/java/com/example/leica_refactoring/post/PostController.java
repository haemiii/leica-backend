package com.example.leica_refactoring.post;

import com.example.leica_refactoring.dto.RequestPostDto;
import com.example.leica_refactoring.dto.ResponsePostDto;
import com.example.leica_refactoring.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    // 전체 게시물 조회
    @GetMapping("/")
    public List<ResponsePostDto> post(){
        List<ResponsePostDto> all = postService.findAll();

        return all;
    }

    // 게시물 생성(ADMIN만 가능)
    @PostMapping("/post")
    public Long createPost(@RequestBody RequestPostDto requestPostDto, @AuthenticationPrincipal UserDetails userDetails){

        Long save = postService.save(requestPostDto, userDetails.getUsername());

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
