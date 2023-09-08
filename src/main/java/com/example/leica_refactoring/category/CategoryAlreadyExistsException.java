package com.example.leica_refactoring.category;

public class CategoryAlreadyExistsException extends RuntimeException{

    CategoryAlreadyExistsException(String name){
        super("이미 존재하는 카테고리 입니다." + name);
    }
}
