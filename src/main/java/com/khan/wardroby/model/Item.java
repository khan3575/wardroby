package com.khan.wardroby.model;

import com.khan.wardroby.model.enums.Category;
import com.khan.wardroby.model.enums.Season;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table(name="items")
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private Users user;

    @NotNull
    @Column(name="name", nullable=false)
    private String name;


    @Column(name="brand")
    private String brand;


    @Column(name="color")
    private String color;

    @NotNull
    @Column(name="image_path", nullable=false)
    private String imagePath;

    @NotNull
    @Column(name="category", nullable=false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="season", nullable=false)
    private Season season;

}
