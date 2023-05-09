package org.server.service;

import org.server.dto.ItemDTO;
import org.server.model.Item;
import org.server.model.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Boolean createItem(ItemDTO itemDTO) {
        Item item = new Item(
            itemDTO.getName(),
            itemDTO.getArtist(),
            itemDTO.getType(),
            itemDTO.getYear(),
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
