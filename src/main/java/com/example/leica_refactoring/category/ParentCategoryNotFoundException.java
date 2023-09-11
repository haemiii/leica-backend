package com.example.leica_refactoring.category;

public class ParentCategoryNotFoundException extends RuntimeException{

    ParentCategoryNotFoundException(String parentName){
        super("존재하지 않는 카테고리입니다." + parentName);
    }
}
