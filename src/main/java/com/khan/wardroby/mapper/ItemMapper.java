package com.khan.wardroby.mapper;

import com.khan.wardroby.dto.ItemDTO;
import com.khan.wardroby.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ItemMapper {
    ItemDTO toDTO(Item item);

    @Mapping(target="id" , ignore=true)
    @Mapping(target="user", ignore=true)
    Item toEntity(ItemDTO itemDTO);
}
