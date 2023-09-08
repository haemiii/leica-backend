package com.example.leica_refactoring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder @Getter
@NoArgsConstructor @AllArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // 부모 삭제시 자식도 삭제
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private final List<Category> child = new ArrayList<>();

    @OneToMany(mappedBy = "childCategory")
    private List<Post> posts = new ArrayList<>();

}
