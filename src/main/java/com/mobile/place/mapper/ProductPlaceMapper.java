package com.mobile.place.mapper;

import com.mobile.place.common.Status;
import com.mobile.place.dto.request.ProductPlaceRequestDTO;
import com.mobile.place.dto.response.ProductPlaceResponseDTO;
import com.mobile.place.entity.ProductHistoryEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPlaceMapper {

    public ProductHistoryEntity map(ProductPlaceRequestDTO dto, Long productId) {
        ProductHistoryEntity productBookedEntity = null;
        if (dto != null) {
            productBookedEntity = new ProductHistoryEntity();
            productBookedEntity.setProductId(productId);
            productBookedEntity.setStatus(Status.getStatus(dto.getStatus()).get().name());
            productBookedEntity.setUserId(dto.getUserId());
        }
        return productBookedEntity;
    }

    public ProductPlaceResponseDTO map(ProductHistoryEntity entity) {
        ProductPlaceResponseDTO dto = null;
        if (entity != null) {
            dto = new ProductPlaceResponseDTO();
            dto.setProductHistoryId(entity.getId());
            dto.setProductId(entity.getProductId());
            dto.setStatus(entity.getStatus());
            dto.setUserId(entity.getUserId());
            dto.setPlacedTime(entity.getCreateTimestamp());
        }
        return dto;
    }
}
