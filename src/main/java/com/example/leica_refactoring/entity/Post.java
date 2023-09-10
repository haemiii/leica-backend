package com.example.leica_refactoring.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Post extends PostTime  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "m_id")
    private Member member;

    private String title;
    private String content;

    @Lob
    private String thumbnail;

    @ManyToOne
    private Category childCategory;


}
