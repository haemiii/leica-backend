package com.example.leica_refactoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class RequestChildCategoryDto {
    private String parentName;
    private String childName;

    private RequestChildCategoryDto(String parentName, String childName){
        this.parentName = parentName;
        this.childName = childName;
    }
}
