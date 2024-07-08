package com.mobile.place.repository;

import com.mobile.place.common.Constant;
import com.mobile.place.exceptions.GenericException;
import com.mobile.place.common.LogFormat;
import com.mobile.place.common.Status;
import com.mobile.place.dto.request.ProductPlaceRequestDTO;
import com.mobile.place.dto.response.ProductResponseDTO;
import com.mobile.place.entity.ProductEntity;
import com.mobile.place.mapper.ProductMapper;
import com.mobile.place.repository.jpa.ProductJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ProductRepository {
    private static final String LOG_OP_INFO = "ProductRepository";

    private final ProductJPARepository jpaRepository;
    private final ProductMapper mapper;

    @Autowired
    ProductRepository(ProductJPARepository jpaRepository, ProductMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    public List<ProductResponseDTO> getAllProduct() {
        try {
            List<ProductEntity> productEntities = jpaRepository.findAll();
            return productEntities.stream().map(req -> mapper.map(req)).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error(LogFormat.error(LOG_OP_INFO, "getByProductBookId - exception caught during processing", ex), ex);
            throw new GenericException(Constant.DATABASE_ERROR);
        }
    }

    @Transactional
    public Integer placeProduct(final Long productId, final ProductPlaceRequestDTO productPlaceRequestDTO) {
        try {
            return jpaRepository.placeProduct(productId,
                    Status.getStatus(productPlaceRequestDTO.getStatus()).get().getAvailable(),
                    productPlaceRequestDTO.getUserId());
        } catch (Exception ex) {
            log.error(LogFormat.error(LOG_OP_INFO, "getByProductBookId - exception caught during processing", ex), ex);
            throw new GenericException(Constant.DATABASE_ERROR);
        }
    }
}
