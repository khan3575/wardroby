package com.khan.wardroby.dao;

import com.khan.wardroby.model.Item;
import com.khan.wardroby.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public List<Item> findAllByUser(Users user);
    public List<Item> findByUserAndCreatedAtAfter(Users user, LocalDateTime since);
    public void delete(Item item);
}
