package com.foodie.restaurantservice.services;

import com.foodie.restaurantservice.dto.MenuItemDTO;
import com.foodie.restaurantservice.dto.RestaurantMenusDTO;
import com.foodie.restaurantservice.entity.MenuItem;
import com.foodie.restaurantservice.entity.Restaurant;
import com.foodie.restaurantservice.repository.MenuItemRepository;
import com.foodie.restaurantservice.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public List<MenuItemDTO> saveRestaurantMenus(RestaurantMenusDTO restaurantMenusDTO) {
        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantMenusDTO.getRestaurantId())
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

            List<MenuItem> menus = restaurantMenusDTO.getMenus().stream().map(this::toMenuEntity).peek(m -> m.setRestaurant(restaurant)).toList();

            List<MenuItem> saved = repository.saveAll(menus);
            return saved.stream().map(this::toDTO).toList();
        } catch (Exception e) {
            log.error("Error saving menu items: {}", e.getMessage());
            throw new IllegalArgumentException("Error while saving menu list");
        }
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
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCategory(entity.getCategory());
        dto.setAvailable(entity.isAvailable());
        return dto;
    }

    private MenuItem toMenuEntity(MenuItemDTO dto) {
        MenuItem entity = new MenuItem();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setCategory(dto.getCategory());
        entity.setAvailable(dto.getAvailable() != null ? dto.getAvailable() : true);
        return entity;
    }
}
