package com.example.leica_refactoring.category;

import com.example.leica_refactoring.dto.RequestChildCategoryDto;
import com.example.leica_refactoring.dto.RequestParentCategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@EnableWebSecurity
//@Rollback(value = false)
class CategoryControllerTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 부모카테고리_생성(){
        RequestParentCategoryDto category = new RequestParentCategoryDto("hello!");
        categoryService.createParentCategory(category);

        assertThat(categoryRepository.count()).isEqualTo(1);
    }

    @Test
    void 자식카테고리_생성_성공(){
        RequestParentCategoryDto category1 = new RequestParentCategoryDto("first");
        RequestParentCategoryDto category2 = new RequestParentCategoryDto("second");

        categoryService.createParentCategory(category1);
        categoryService.createParentCategory(category2);

        RequestChildCategoryDto childCategoryDto = new RequestChildCategoryDto("first", "hi");
        Long childCategory = categoryService.createChildCategory(childCategoryDto);

        assertThat(categoryRepository.findById(childCategory).get().getParent().getName()).isEqualTo("first");
    }

    @Test
    void 자식카테고리_생성_실패(){
        RequestParentCategoryDto category1 = new RequestParentCategoryDto("first");

        categoryService.createParentCategory(category1);

        Assertions.assertThrows(ParentCategoryNotFoundException.class, () -> {
            Long childCategoryId = categoryService.createChildCategory(new RequestChildCategoryDto("second", "hi"));
        });
    }


}