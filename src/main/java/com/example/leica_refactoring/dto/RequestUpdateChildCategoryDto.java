package com.example.leica_refactoring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestUpdateChildCategoryDto {
    private String childName;

    public void RequestChildCategoryDto(String childName){
        this.childName = childName;
    }
}
