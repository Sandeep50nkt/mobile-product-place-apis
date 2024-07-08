package com.mobile.place.service;

import com.mobile.place.common.Status;
import com.mobile.place.dto.request.ProductPlaceRequestDTO;
import com.mobile.place.dto.response.ProductPlaceResponseDTO;
import com.mobile.place.repository.ProductPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPlaceService {
    private final ProductPlaceRepository repository;

    @Autowired
    ProductPlaceService(ProductPlaceRepository repository) {
        this.repository = repository;
    }

    public ProductPlaceResponseDTO placeProduct(final ProductPlaceRequestDTO productPlaceRequestDTO, final Long productId) {
        return repository.saveProductPlaceRequest(productPlaceRequestDTO, productId);
    }

    public List<ProductPlaceResponseDTO> getProductHistoryByProductId(final Long productId) {
        return repository.getByProductId(productId);
    }

    public ProductPlaceResponseDTO getProductHistoryById(final Long productId) {
        return repository.getProductHistoryById(productId);
    }

    public ProductPlaceResponseDTO getLatestProductHistoryByProductId(Long productId, String status) {
        return repository.getLatestProductHistoryByProductId(productId, Status.getStatus(status).get().name());
    }
}
