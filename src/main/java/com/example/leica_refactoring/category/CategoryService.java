package com.example.leica_refactoring.category;

import com.example.leica_refactoring.dto.RequestChildCategoryDto;
import com.example.leica_refactoring.dto.RequestParentCategoryDto;
import com.example.leica_refactoring.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public Long createParentCategory(RequestParentCategoryDto parentCategory) {
        String parentName = parentCategory.getParentName();
        Category category = categoryRepository.findByName(parentName);
        if(category != null){
            throw new CategoryAlreadyExistsException(parentName);
        }else {
            Category category1 = Category.builder()
                    .name(parentName)
                    .parent(null)
                    .build();

            Category save = categoryRepository.save(category1);
            return save.getId();
        }

    }

    public Long createChildCategory(RequestChildCategoryDto childCategory) {
        Category parentCategory = categoryRepository.findByName(childCategory.getParentName());
        if(parentCategory == null){
            throw new ParentCategoryNotFoundException(childCategory.getParentName());
        }else{
            String childName = childCategory.getChildName();
            Category category = Category.builder()
                    .name(childName)
                    .parent(parentCategory)
                    .build();

            Category save = categoryRepository.save(category);
            return save.getId();
        }


    }
}
