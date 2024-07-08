package com.mobile.place.mapper;

import com.mobile.place.dto.response.ProductResponseDTO;
import com.mobile.place.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDTO map(ProductEntity entity) {
        ProductResponseDTO productResponseDTO = null;
        if (entity != null) {
            productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setProductId(entity.getId());
            productResponseDTO.setProductName(entity.getName());
            productResponseDTO.setImageURL(entity.getImageURL());
            productResponseDTO.setDescription(entity.getDescription());
            productResponseDTO.setBrand(entity.getBrand());
            productResponseDTO.setUserId(entity.getModifiedBy());
            productResponseDTO.setIsAvailable(entity.getIsAvailable());
            productResponseDTO.setCreatedTime(entity.getCreateTimestamp());
            productResponseDTO.setUpdatedTime(entity.getModifiedTimestamp());
        }
        return productResponseDTO;
    }
}
