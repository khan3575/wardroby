package com.khan.wardroby.service;

import com.khan.wardroby.dao.ItemRepository;
import com.khan.wardroby.dto.ItemDTO;
import com.khan.wardroby.exception.ItemException;
import com.khan.wardroby.mapper.ItemMapper;
import com.khan.wardroby.model.Item;
import com.khan.wardroby.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    // we will define service here.
    private final ItemRepository repository;
    private final ImageStorageService imgService;
    private final ItemMapper itemMapper;



    public ItemService(ItemRepository repository, ImageStorageService imgService, ItemMapper itemMapper) {
        this.repository = repository;
        this.imgService = imgService;
        this.itemMapper = itemMapper;
    }

    @Transactional
    public void addItem(ItemDTO itemDTO, Users user) {
        if(itemDTO.getImageFile() == null || itemDTO.getImageFile().isEmpty())
        {
            throw new ItemException("Image is empty");
        }
        String savedPath = imgService.uploadImage(itemDTO.getImageFile(),user.getId());
        Item itemEntity = itemMapper.toEntity(itemDTO);
        itemEntity.setImagePath(savedPath);
        itemEntity.setUser(user);
        repository.save(itemEntity);

    }

    public void deleteItem(Item item)
    {
        repository.delete(item);
    }



}
