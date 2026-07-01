package com.khan.wardroby.dao;

import com.khan.wardroby.model.Item;
import com.khan.wardroby.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public List<Item> findAllByUser(Users user);
}
