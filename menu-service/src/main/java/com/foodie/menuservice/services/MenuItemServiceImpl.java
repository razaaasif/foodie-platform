package com.foodie.menuservice.services;

import com.foodie.menuservice.dto.MenuItemDTO;
import com.foodie.menuservice.entity.MenuItem;
import com.foodie.menuservice.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;

    @Override
    public MenuItemDTO save(MenuItemDTO dto) {
        validate(dto);
        MenuItem entity = toEntity(dto);
        MenuItem saved = repository.save(entity);
        return toDTO(saved);
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

    private void validate(MenuItemDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("MenuItemDTO cannot be null");
        }
        if (!StringUtils.hasText(dto.getName())) {
            throw new IllegalArgumentException("MenuItem name is required");
        }
        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new IllegalArgumentException("MenuItem price must be positive");
        }
        if (!StringUtils.hasText(dto.getCategory())) {
            throw new IllegalArgumentException("MenuItem category is required");
        }
        if (dto.getRestaurantId() == null || dto.getRestaurantId() <= 0) {
            throw new IllegalArgumentException("Restaurant ID is required");
        }
    }

    private MenuItemDTO toDTO(MenuItem entity) {
        MenuItemDTO dto = new MenuItemDTO();
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
        entity.setId(dto.getRestaurantId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setCategory(dto.getCategory());
        entity.setRestaurantId(dto.getRestaurantId());
        entity.setAvailable(dto.getAvailable() != null ? dto.getAvailable() : true);
        return entity;
    }
}
