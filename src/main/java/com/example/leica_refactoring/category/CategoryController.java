package com.example.leica_refactoring.category;

import com.example.leica_refactoring.dto.RequestChildCategoryDto;
import com.example.leica_refactoring.dto.RequestParentCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create/category/parent")
    public Long createParentCategory(@RequestBody RequestParentCategoryDto parentCategory){
        return categoryService.createParentCategory(parentCategory);
    }

    @PostMapping("/create/category/child")
    public Long createChildCategory(@RequestBody RequestChildCategoryDto childCategory){
        return categoryService.createChildCategory(childCategory);
    }


}
