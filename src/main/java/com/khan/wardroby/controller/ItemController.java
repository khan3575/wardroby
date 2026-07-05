package com.khan.wardroby.controller;

import com.khan.wardroby.dto.ItemDTO;
import com.khan.wardroby.mapper.ItemMapper;
import com.khan.wardroby.model.Item;
import com.khan.wardroby.model.Users;
import com.khan.wardroby.model.enums.Category;
import com.khan.wardroby.model.enums.Season;
import com.khan.wardroby.service.ImageStorageService;
import com.khan.wardroby.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final ImageStorageService imgService;
    @Autowired
    public ItemController( ItemMapper itemMapper, ItemService itemService, ImageStorageService imgService) {
        this.itemMapper = itemMapper;
        this.itemService = itemService;
        this.imgService = imgService;
    }

    @ModelAttribute("categories")
    public Category[] categories(){
        return Category.values();
    }

    @ModelAttribute("seasons")
    public Season[] seasons()
    {
        return Season.values();
    }
    //here we will develop feature for the add item.
    // item controller will need service to add item.
    @GetMapping("/add-item")
    public String getAddItemForm(Model model)
    {
        model.addAttribute("itemDTO", new ItemDTO());
        return "add-item-form";
    }

    @PostMapping("/add-item")
    public String postAddItemForm(@Valid @ModelAttribute("itemDTO") ItemDTO itemDTO, BindingResult result, @AuthenticationPrincipal Users currentUser, Model model)
    {
        if(result.hasErrors())
        {
            return "add-item-form";
        }
        if (itemDTO.getCategory() == null) {
            model.addAttribute("errorMessage", "Please select a category.");
            return "add-item-form";
        }
        if (itemDTO.getImageFile() == null || itemDTO.getImageFile().isEmpty()) {
            model.addAttribute("errorMessage", "Please upload an image file.");
            return "add-item-form";
        }
        String savedPath = imgService.uploadImage(itemDTO.getImageFile(),currentUser.getId());
        Item itemEntity = itemMapper.toEntity(itemDTO);
        itemEntity.setImagePath(savedPath);
        itemService.addItem(itemEntity, currentUser);


        return "redirect:/dashboard";
    }


}
