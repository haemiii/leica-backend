package com.example.leica_refactoring.category;

public class CategoryIsNotExists extends RuntimeException{
    CategoryIsNotExists(String name){
        super("존재하지 않는 카테고리 이름입니다." + name);
    }
}
