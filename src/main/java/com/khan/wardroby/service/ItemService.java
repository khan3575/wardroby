package com.khan.wardroby.service;

import com.khan.wardroby.dao.ItemRepository;
import com.khan.wardroby.model.Item;
import com.khan.wardroby.model.Users;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    // we will define service here.
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public void addItem(Item item, Users user) {
        item.setUser(user);
        repository.save(item);
    }
}
