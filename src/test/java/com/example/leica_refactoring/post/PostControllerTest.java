package com.example.leica_refactoring.post;

import com.example.leica_refactoring.dto.RequestPostDto;
import com.example.leica_refactoring.entity.Member;
import com.example.leica_refactoring.entity.Post;
import com.example.leica_refactoring.member.MemberAccount;
import com.example.leica_refactoring.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@EnableWebSecurity
//@Rollback(value = false)
class PostControllerTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;


    @Test
    void 게시물_등록() {

        Member member = memberRepository.findByMemberId("hamm");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new MemberAccount(member), //principal
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        RequestPostDto requestPostDto = new RequestPostDto("hi", "wow", "not found");
        String name = context.getAuthentication().getName();

        Long save = postService.save(requestPostDto, name);
        assertThat(postRepository.count()).isEqualTo(2);

    }

    @Test
    void 게시물_수정(){
        Member member = memberRepository.findByMemberId("hamm");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new MemberAccount(member), //principal
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        String name = context.getAuthentication().getName();


        RequestPostDto requestPostDto = new RequestPostDto("mmh", "nice", "abc.jpg");

        Long update = postService.update(1L, requestPostDto, name);

        Optional<Post> byId = postRepository.findById(update);

        byId.ifPresent(b -> {
            if (b.getTitle() != null && b.getTitle().contains("good")) {
                System.out.println("perfect");
            }
        });

    }

    @Test
    void 게시물_수정_실패(){
        Member member = memberRepository.findByMemberId("chaco");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new MemberAccount(member), //principal
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        String name = context.getAuthentication().getName();


        RequestPostDto requestPostDto = new RequestPostDto("mmh", "nice", "abc.jpg");

        Assertions.assertThrows(AuthorOnlyAccessException.class, ()->{
            Long update = postService.update(2L, requestPostDto, name);
        });

    }

    @Test
    void 게시물_삭제(){
        Member member = memberRepository.findByMemberId("hamm");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new MemberAccount(member), //principal
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        String name = context.getAuthentication().getName();

        postService.delete(2L,name);
    }
    @Test
    void 게시물_삭제_실패(){
        Member member = memberRepository.findByMemberId("chaco");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new MemberAccount(member), //principal
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        String name = context.getAuthentication().getName();

        Assertions.assertThrows(AuthorOnlyAccessException.class, ()->{
            postService.delete(2L, name);
        });
    }


}