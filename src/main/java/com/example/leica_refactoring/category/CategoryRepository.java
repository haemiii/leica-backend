package com.example.leica_refactoring.category;

import com.example.leica_refactoring.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String categoryName);

    List<Category> findAllByName(String childName);
}
