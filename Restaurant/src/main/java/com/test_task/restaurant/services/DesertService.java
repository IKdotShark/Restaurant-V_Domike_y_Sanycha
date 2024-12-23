package com.test_task.restaurant.services;

import com.test_task.restaurant.exception.ResourceNotFoundException;
import com.test_task.restaurant.models.Desert;
import com.test_task.restaurant.repositories.DesertRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DesertService {

    private final DesertRepository desertRepository;

    public DesertService(DesertRepository desertRepository) {
        this.desertRepository = desertRepository;
    }

    public Desert createDesert(Desert desert) {
        if (desert.getTransientIngredients() != null) {
            String ingredients = String.join(",", desert.getTransientIngredients());
            desert.setIngredients(ingredients);
        }
        return desertRepository.save(desert);
    }

    public Desert findDesertById(Long id) {
        return desertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found desert with such " + id));
    }

    public List<Desert> findDesertsByIds(List<Long> ids) {
        List<Desert> deserts = new ArrayList<>();
        for (Long id : ids) {
            desertRepository.findById(id).ifPresent(desert -> {
                if (desert.getIngredients() != null) {
                    List<String> ingredientsList = Arrays.asList(desert.getIngredients().split(","));
                    desert.setTransientIngredients(ingredientsList);
                }
                deserts.add(desert);
            });
        }
        return deserts;
    }

    public List<Desert> findAllDeserts() {
        return desertRepository.findAll();
    }

    public void deleteDesertById(Long id) {
        Optional<Desert> desert = desertRepository.findById(id);
        if (desert.isEmpty()) throw new ResourceNotFoundException("Not found desert with such " + id);
        desertRepository.deleteById(id);
    }

    public Desert updateDesert(Desert desert, Desert desertInfo) {
        if (desertInfo.getName() != null) {
            desert.setName(desertInfo.getName());
        }

        if (desertInfo.getPrice() != desert.getPrice()) {
            desert.setPrice(desertInfo.getPrice());
        }

        if (desertInfo.getTransientIngredients() != null) {
            String ingredients = String.join(",", desertInfo.getTransientIngredients());
            desert.setIngredients(ingredients);
            desert.setTransientIngredients(desertInfo.getTransientIngredients());
        } else {
            List<String> ingredientsList = Arrays.asList(desert.getIngredients().split(","));
            desert.setTransientIngredients(ingredientsList);
        }

        if (desertInfo.getDescription() != null) {
            desert.setDescription(desertInfo.getDescription());
        }

        if (desertInfo.getCategory() != null) {
            desert.setCategory(desertInfo.getCategory());
        }

        if (desertInfo.getSrc() != null) {
            desert.setSrc(desertInfo.getSrc());
        }
        return desertRepository.save(desert);
    }
}