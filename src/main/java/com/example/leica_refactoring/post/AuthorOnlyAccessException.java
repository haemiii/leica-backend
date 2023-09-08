package com.example.leica_refactoring.post;

public class AuthorOnlyAccessException extends RuntimeException{

    AuthorOnlyAccessException(){
        super("작성자만 수정할 수 있습니다.");
    }
}
