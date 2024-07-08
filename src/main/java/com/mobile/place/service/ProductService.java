package com.mobile.place.service;


import com.mobile.place.dto.request.ProductPlaceRequestDTO;
import com.mobile.place.dto.response.ProductResponseDTO;
import com.mobile.place.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponseDTO> getAllProducts() {
        return repository.getAllProduct();
    }

    public Integer placeProduct(Long productId, ProductPlaceRequestDTO productPlaceRequestDTO) {
        return repository.placeProduct(productId, productPlaceRequestDTO);
    }
}
