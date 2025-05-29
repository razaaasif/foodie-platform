package com.foodie.menuservice.services;

import com.foodie.menuservice.dto.MenuItemDTO;
import com.foodie.menuservice.entity.MenuItem;
import com.foodie.menuservice.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Service
@AllArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;

    @Override
    public List<MenuItemDTO> save(List<MenuItemDTO> dto) {
        try {
            List<MenuItem> entity = dto.stream().map(this::toEntity).toList();
            List<MenuItem> saved = repository.saveAll(entity);
            return saved.stream().map(this::toDTO).toList();
        } catch (Exception e) {
            log.error("Error saving menu items: {}" , e.getMessage());
            throw new IllegalArgumentException("Error while saving menu list");
        }
    }


    @Override
    public List<MenuItemDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getByRestaurant(Long restaurantId) {
        if (restaurantId == null || restaurantId <= 0) {
            throw new IllegalArgumentException("Invalid restaurant ID");
        }
        return repository.findByRestaurantId(restaurantId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> searchByName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Search name must not be empty");
        }
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MenuItemDTO toDTO(MenuItem entity) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(entity.getId());
        dto.setRestaurantId(entity.getRestaurantId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCategory(entity.getCategory());
        dto.setAvailable(entity.isAvailable());
        return dto;
    }

    private MenuItem toEntity(MenuItemDTO dto) {
        MenuItem entity = new MenuItem();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setCategory(dto.getCategory());
        entity.setRestaurantId(dto.getRestaurantId());
        entity.setAvailable(dto.getAvailable() != null ? dto.getAvailable() : true);
        return entity;
    }
}
