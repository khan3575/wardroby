package com.khan.wardroby.dto;

import com.khan.wardroby.model.Users;
import com.khan.wardroby.model.enums.Category;
import com.khan.wardroby.model.enums.Season;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;


public class ItemDTO {

    @NotNull
    private String name;


    private String brand;

    @NotNull
    private String color;

    @NotNull
    private String imagePath;

    private Category category;

    @NotNull
    private Season season;
    public ItemDTO(){}

    public ItemDTO(String name, String brand, String color, String imagePath, Category category, Season season) {
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.imagePath = imagePath;
        this.category = category;
        this.season = season;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }


    @Override
    public String toString() {
        return "ItemDTO{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", category=" + category +
                ", season=" + season +
                '}';
    }
}
