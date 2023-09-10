package com.example.leica_refactoring.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;

@Data
@JsonDeserialize(builder = RequestPostDto.Builder.class)
public class RequestPostDto {

    private String title;
    private String content;
    private String thumbnail;
    private String parentName;
    private String childName;


    private RequestPostDto(String title, String content, String thumbnail, String parentName, String childName) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.parentName = parentName;
        this.childName = childName;
    }

    @JsonPOJOBuilder
    static class Builder{
        String title;
        String content;
        String thumbnail;
        String parentName;
        String childName;

        Builder withTitle(String title){
            this.title = title;
            return this;
        }

        Builder withContent(String content){
            this.content = content;
            return this;
        }

        Builder withThumbnail(String thumbnail){
            this.thumbnail = thumbnail;
            return this;
        }

        Builder withParentName(String parentName){
            this.parentName = parentName;
            return this;
        }

        Builder withChildName(String childName){
            this.childName = childName;
            return this;
        }

        public RequestPostDto build(){
            return new RequestPostDto(title,content, thumbnail, parentName, childName);
        }
    }

}

