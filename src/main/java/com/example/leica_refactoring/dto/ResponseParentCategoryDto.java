package com.example.leica_refactoring.dto;

import com.example.leica_refactoring.entity.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseParentCategoryDto {
    private String name;
    private Category parent;
}
