package com.example.leica_refactoring.search;

import com.example.leica_refactoring.dto.ResponsePostDto;
import com.example.leica_refactoring.dto.ResponsePostListDto;
import com.example.leica_refactoring.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    @Transactional
    public ResponsePostListDto SearchPost (String keyword){
        List<Post> postList = searchRepository.findByTitleContainingOrContentContaining(keyword, keyword);

        int size = postList.size();

        List<ResponsePostDto> collect = postList.stream().map(post ->
                        ResponsePostDto.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .thumbnail(post.getThumbnail())
                                .category(post.getChildCategory().getName())
                                .writer(post.getMember().getMemberId())
                                .build())
                .collect(Collectors.toList());

        ResponsePostListDto build = ResponsePostListDto.builder()
                .size((long) size)
                .childList(collect)
                .build();
        return build;
    }
}
