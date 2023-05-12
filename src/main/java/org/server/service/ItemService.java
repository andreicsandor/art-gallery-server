package org.server.service;

import org.server.dto.ItemDTO;
import org.server.model.Exhibit;
import org.server.model.Item;
import org.server.model.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems() {
        List<Item> items = this.itemRepository.findAll();

        return items;
    }

    public Boolean createItem(ItemDTO itemDTO) {
        Item item = new Item(
            itemDTO.getName(),
            itemDTO.getArtist(),
            itemDTO.getType(),
            itemDTO.getYear(),
            itemDTO.getImage(),
            itemDTO.getPrice(),
            itemDTO.getBuyer(),
            itemDTO.getSaleDate(),
            itemDTO.getDeliveryDate()
        );

        try {
            // Save the created Item
            this.itemRepository.save(item);

            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
