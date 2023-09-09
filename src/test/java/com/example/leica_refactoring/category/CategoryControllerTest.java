//package com.example.leica_refactoring.category;
//
//import com.example.leica_refactoring.dto.RequestChildCategoryDto;
//import com.example.leica_refactoring.dto.RequestParentCategoryDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.NoSuchElementException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//
//@SpringBootTest
//@Transactional
//@EnableWebSecurity
////@Rollback(value = false)
//class CategoryControllerTest {
//
//    @Autowired
//    CategoryService categoryService;
//
//    @Autowired
//    CategoryRepository categoryRepository;
//
//    @Test
//    void 부모카테고리_생성(){
//        RequestParentCategoryDto category = new RequestParentCategoryDto("hello");
//        Long id = categoryService.createParentCategory(category);
//
//        assertThat(categoryRepository.findById(id).get().getName()).isEqualTo("hello");
//    }
//
//    @Test
//    void 자식카테고리_생성_성공(){
//        RequestParentCategoryDto category1 = new RequestParentCategoryDto("first");
//        RequestParentCategoryDto category2 = new RequestParentCategoryDto("second");
//
//        categoryService.createParentCategory(category1);
//        categoryService.createParentCategory(category2);
//
//        RequestChildCategoryDto childCategoryDto = new RequestChildCategoryDto("first", "hi");
//        Long childCategory = categoryService.createChildCategory(childCategoryDto);
//
//        assertThat(categoryRepository.findById(childCategory).get().getParent().getName()).isEqualTo("first");
//    }
//
//    @Test
//    void 자식카테고리_생성_실패(){
//        RequestParentCategoryDto category1 = new RequestParentCategoryDto("first");
//
//        categoryService.createParentCategory(category1);
//
//        Assertions.assertThrows(ParentCategoryNotFoundException.class, () -> {
//            Long childCategoryId = categoryService.createChildCategory(new RequestChildCategoryDto("second", "hi"));
//        });
//    }
//
//
//    @Test   // 보완 필요! 실제로 삭제는 되지만 Expecting an empty Optional but was containing value 에러 발생
//    void 부모카테고리_삭제(){
//
//        Long parentCategoryId = 1L;
//        Long childCategoryId = 3L;
//        categoryService.deleteCategory(parentCategoryId);
//
//        assertThat(categoryRepository.findById(parentCategoryId)).isEmpty();
//
//        // 삭제된 자식 카테고리를 찾아보고, 존재하지 않아야 함
//        assertThat(categoryRepository.findById(childCategoryId)).isEmpty();
//
//    }
//
//    @Test
//    public void 카테고리_삭제_실패_존재하지_않는_카테고리() {
//        // 존재하지 않는 카테고리 ID
//        Long nonExistentCategoryId = 9999L;
//
//        // 카테고리 삭제 시도, 존재하지 않는 카테고리를 삭제하려면 예외가 발생해야 함
//        try {
//            categoryService.deleteCategory(nonExistentCategoryId);
//        } catch (NoSuchElementException e) {
//            // 예상한대로 예외 발생
//            assertThat(e.getMessage()).isEqualTo("존재하지 않는 카테고리 입니다.");
//        }
//    }
//
//    @Test
//    void 자식카테고리_삭제(){
//        Long categoryId = 2L;
//        categoryService.deleteCategory(categoryId);
//
//        assertFalse(categoryRepository.findById(categoryId).isPresent());
//    }
//
//}