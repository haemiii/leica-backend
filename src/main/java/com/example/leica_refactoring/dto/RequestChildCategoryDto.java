package com.example.leica_refactoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RequestChildCategoryDto {
    private String parentName;
    private String childName;
}
