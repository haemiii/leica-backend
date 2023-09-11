package com.example.leica_refactoring.dto;

import lombok.Data;

@Data
public class RequestPostDto {

    private String title;
    private String content;
    private String thumbnail;


    public RequestPostDto(String title, String content, String thumbnail) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
    }

}

