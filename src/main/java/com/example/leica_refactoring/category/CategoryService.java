package com.example.leica_refactoring.category;

import com.example.leica_refactoring.dto.RequestChildCategoryDto;
import com.example.leica_refactoring.dto.RequestParentCategoryDto;
import com.example.leica_refactoring.dto.ResponseChildCategoryDto;
import com.example.leica_refactoring.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<ResponseChildCategoryDto> findAllChildCategory(String parentCategory) {
        Category category = categoryRepository.findByName(parentCategory);

        List<ResponseChildCategoryDto> childCategoryDtos = category.getChild().stream()
                .map(childCategory -> ResponseChildCategoryDto.builder()
                        .id(childCategory.getId())
                        .childName(childCategory.getName()) // 자식 카테고리의 필드를 적절히 매핑
                        .build())
                .collect(Collectors.toList());

        return childCategoryDtos;

    }
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

    public void deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        category.ifPresentOrElse(c -> categoryRepository.deleteById(categoryId),
                ()-> {
                    throw new NoSuchElementException("존재하지 않는 카테고리 입니다.");
                    });
    }


}
