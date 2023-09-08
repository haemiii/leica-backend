package com.example.leica_refactoring.dto;

import lombok.Data;

@Data
public class RequestPostDto {

    private String title;
    private String content;
    private String thumbnail;
    private String parentName;
    private String childName;


    public RequestPostDto(String title, String content, String thumbnail, String parentName, String childName) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.parentName = parentName;
        this.childName = childName;
    }
}

