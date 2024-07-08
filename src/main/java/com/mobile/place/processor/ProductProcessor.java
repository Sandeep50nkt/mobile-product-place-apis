package com.mobile.place.processor;

import com.mobile.place.common.LogFormat;
import com.mobile.place.dto.request.ProductPlaceRequestDTO;
import com.mobile.place.dto.response.ProductPlaceAPIResponseDTO;
import com.mobile.place.dto.response.ProductPlaceResponseDTO;
import com.mobile.place.dto.response.ProductResponseDTO;
import com.mobile.place.service.ProductPlaceService;
import com.mobile.place.service.ProductService;
import com.mobile.place.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Service
public class ProductProcessor {
    private static final String LOG_OP_INFO = "ProductProcessor";

    private final ProductService productService;
    private final ProductPlaceService productPlaceService;
    private final ProductMessageSender productMessageSender;

    @Autowired
    ProductProcessor(ProductService productService, ProductPlaceService productPlaceService,
                     ProductMessageSender productMessageSender) {
        this.productService = productService;
        this.productPlaceService = productPlaceService;
        this.productMessageSender = productMessageSender;
    }

    public ProductPlaceAPIResponseDTO processPlaceProductRequest(final Long productId,
                                                                 final ProductPlaceRequestDTO productPlaceRequestDTO) {
        ProductPlaceAPIResponseDTO productPlaceResponse = placeProduct(productId, productPlaceRequestDTO);
        String sessionId = MDC.get(LogFormat.MDC_SESSIONID);
        productMessageSender.sendMessage(productId, productPlaceResponse, sessionId);
        return productPlaceResponse;
    }

    @Transactional
    public ProductPlaceAPIResponseDTO placeProduct(final Long productId,
                                                   final ProductPlaceRequestDTO productPlaceRequestDTO) {
        Integer noOfRowUpdated = productService.placeProduct(productId, productPlaceRequestDTO);
        ProductPlaceAPIResponseDTO productPlaceResponse = new ProductPlaceAPIResponseDTO();

        log.info(LogFormat.LOGGER_OK_PATTERN, LOG_OP_INFO,
                MessageFormat.format("placeProduct - no of records updated={0} for product_id={1}", noOfRowUpdated, productId));

        if (noOfRowUpdated > 0) {
            ProductPlaceResponseDTO productPlaceResponseDTO = productPlaceService.placeProduct(productPlaceRequestDTO, productId);
            log.info(LogFormat.LOGGER_OK_PATTERN, LOG_OP_INFO,
                    MessageFormat.format("placeProduct - successfully for product_id={0} with response", productId, JsonUtil.toMessageJson(productPlaceRequestDTO)));
            productPlaceResponse.setSuccess(true);
            productPlaceResponse.setResponseBody(productPlaceResponseDTO);
        } else {
            ProductPlaceResponseDTO productPlaceResponseDTO = productPlaceService.getLatestProductHistoryByProductId(productId, productPlaceRequestDTO.getStatus());
            productPlaceResponse.setSuccess(false);
            productPlaceResponse.setResponseBody(productPlaceResponseDTO);
        }
        return productPlaceResponse;
    }

    public List<ProductPlaceResponseDTO> getProductHistoryByProductId(Long productId) {
        return productPlaceService.getProductHistoryByProductId(productId);
    }

    public ProductPlaceResponseDTO getProductHistoryById(final Long productHistoryId) {
        return productPlaceService.getProductHistoryById(productHistoryId);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

}
