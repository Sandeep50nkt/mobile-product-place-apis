package com.mobile.place.repository;

import com.mobile.place.common.Constant;
import com.mobile.place.exceptions.GenericException;
import com.mobile.place.common.LogFormat;
import com.mobile.place.dto.request.ProductPlaceRequestDTO;
import com.mobile.place.dto.response.ProductPlaceResponseDTO;
import com.mobile.place.entity.ProductHistoryEntity;
import com.mobile.place.mapper.ProductPlaceMapper;
import com.mobile.place.repository.jpa.ProductPlaceJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ProductPlaceRepository {
    private static final String LOG_OP_INFO = "ProductPlaceRepository";

    private final ProductPlaceJPARepository jpaRepository;
    private final ProductPlaceMapper mapper;

    @Autowired
    ProductPlaceRepository(ProductPlaceJPARepository jpaRepository, ProductPlaceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    public ProductPlaceResponseDTO saveProductPlaceRequest(final ProductPlaceRequestDTO productPlaceRequestDTO, final Long productId) {
        ProductHistoryEntity toBeSaveEntity = mapper.map(productPlaceRequestDTO, productId);
        try {
            ProductHistoryEntity savedEntity = jpaRepository.saveAndFlush(toBeSaveEntity);
            return mapper.map(savedEntity);
        } catch (Exception ex) {
            log.error(LogFormat.error(LOG_OP_INFO, "saveProductPlaceRequest - exception caught during processing", ex), ex);
            throw new GenericException(Constant.DATABASE_ERROR);
        }
    }

    public List<ProductPlaceResponseDTO> getByProductId(final Long productId) {
        try {
            List<ProductHistoryEntity> entities = jpaRepository.getProductBookEntitiesByProductId(productId);
            return entities.stream().map(entity -> mapper.map(entity)).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error(LogFormat.error(LOG_OP_INFO, "getByProductId - exception caught during processing", ex), ex);
            throw new GenericException(Constant.DATABASE_ERROR);
        }
    }

    public ProductPlaceResponseDTO getProductHistoryById(final Long productBookId) {
        try {
            Optional<ProductHistoryEntity> optionalProductHistoryEntity = jpaRepository.findById(productBookId);
            if (!optionalProductHistoryEntity.isEmpty())
                return mapper.map(optionalProductHistoryEntity.get());
        } catch (Exception ex) {
            log.error(LogFormat.error(LOG_OP_INFO, "getByProductBookId - exception caught during processing", ex), ex);
            throw new GenericException(Constant.DATABASE_ERROR);
        }
        return null;
    }

    public ProductPlaceResponseDTO getLatestProductHistoryByProductId(final Long productId, final String status) {
        try {
            ProductHistoryEntity entity = jpaRepository.getLatestProductHistoryByProductId(productId, status);
            return mapper.map(entity);
        } catch (Exception ex) {
            log.error(LogFormat.error(LOG_OP_INFO, "getLatestProductHistoryByProductId - exception caught during processing", ex), ex);
            throw new GenericException(Constant.DATABASE_ERROR);
        }
    }
}
