package com.khan.wardroby.dto;

import com.khan.wardroby.model.enums.Category;
import com.khan.wardroby.model.enums.Season;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    @NotNull
    private String name;


    private String brand;

    @NotNull
    private String color;


    private String imagePath;

    @NotNull
    private Category category;

    @NotNull
    private Season season;

    private MultipartFile imageFile;
}
