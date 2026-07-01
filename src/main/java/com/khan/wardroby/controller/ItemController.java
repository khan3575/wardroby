package com.khan.wardroby.controller;

import com.khan.wardroby.dto.ItemDTO;
import com.khan.wardroby.exception.InvalidItemDefinitionException;
import com.khan.wardroby.mapper.ItemMapper;
import com.khan.wardroby.model.Item;
import com.khan.wardroby.model.Users;
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
    @Autowired
    public ItemController( ItemMapper itemMapper, ItemService itemService) {
        this.itemMapper = itemMapper;
        this.itemService = itemService;
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
    public String postAddItemForm(@Valid @ModelAttribute("itemDTO") ItemDTO itemDTO, BindingResult result, @AuthenticationPrincipal Users currentUser)
    {
        if(result.hasErrors())
        {
            return "add-item-form";
        }
        Item itemEntity = itemMapper.toEntity(itemDTO);
        itemService.addItem(itemEntity, currentUser);


        return "dashboard";
    }


}
