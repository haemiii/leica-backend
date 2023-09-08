package com.example.leica_refactoring.search;

import com.example.leica_refactoring.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SearchRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingOrContentContaining(String keyword, String keyword2);

}
