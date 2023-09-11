package com.example.leica_refactoring.search;

import com.example.leica_refactoring.dto.ResponsePostListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    // 게시물 검색(완성)
    @GetMapping("/search/post")
    public ResponsePostListDto searchPost(@RequestParam(value = "keyword")String keyword){
        ResponsePostListDto responsePostListDto = searchService.SearchPost(keyword);

        return responsePostListDto;
    }
}
