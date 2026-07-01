package com.khan.wardroby.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="authorities")
public class Authority {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="authority", unique = true)
    private String authority;


}
