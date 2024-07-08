package com.mobile.place.controllers;

import com.mobile.place.common.*;
import com.mobile.place.dto.request.ProductPlaceRequestDTO;
import com.mobile.place.dto.response.ProductPlaceAPIResponseDTO;
import com.mobile.place.dto.response.ProductPlaceResponseDTO;
import com.mobile.place.dto.response.ProductResponseDTO;
import com.mobile.place.exceptions.InvalidDataRequestException;
import com.mobile.place.processor.ProductProcessor;
import com.mobile.place.util.ErrorResponseMaker;
import com.mobile.place.util.JsonUtil;
import com.mobile.place.validator.ProductPlaceRequestDTOValidator;
import com.mobile.place.validator.ValidationErrorParser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    private static final String LOG_OP_INFO = "ProductController";

    @InitBinder("productPlaceRequestDTO")
    protected void initBinderForProductPlaceRequest(WebDataBinder binder) {
        binder.addValidators(productPlaceRequestDTOValidator);
    }

    private final ProductPlaceRequestDTOValidator productPlaceRequestDTOValidator;
    private final ProductProcessor processor;

    @Autowired
    ProductController(@Qualifier("ProductPlaceRequestDTOValidator")
                      ProductPlaceRequestDTOValidator productPlaceRequestDTOValidator,
                      ProductProcessor processor) {
        this.productPlaceRequestDTOValidator = productPlaceRequestDTOValidator;
        this.processor = processor;

    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> productRequestDTOS = processor.getAllProducts();
        return new ResponseEntity<>(productRequestDTOS, HttpStatus.OK);
    }

    @PostMapping("/{product_id}")
    public ResponseEntity<?> placeProduct(@RequestBody @Valid ProductPlaceRequestDTO productPlaceRequestDTO,
                                          final BindingResult bindingResult,
                                          @PathVariable("product_id") Long productId
    ) {
        MDC.put(LogFormat.MDC_SESSIONID, UUID.randomUUID().toString());
        log.info(LogFormat.LOGGER_OK_PATTERN, LOG_OP_INFO,
                MessageFormat.format("placeProduct - invoked for product_id={0} with payload={1}", productId, JsonUtil.toMessageJson(productPlaceRequestDTO)));
        try {
            ValidationErrorParser.processErrors(bindingResult);
            ProductPlaceAPIResponseDTO productPlaceResponse = processor.processPlaceProductRequest(productId, productPlaceRequestDTO);
            return new ResponseEntity<>(productPlaceResponse, HttpStatus.OK);
        } catch (InvalidDataRequestException ex) {
            log.error(LogFormat.error(LOG_OP_INFO, "placeProduct - exception while processing", ex), ex);
            return new ResponseEntity<>(ErrorResponseMaker.getErrorObject(Constant.EXCEPTION, ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error(LogFormat.error(LOG_OP_INFO, "placeProduct - Exception while processing", ex), ex);
            return new ResponseEntity<>(ErrorResponseMaker.getErrorObject(Constant.EXCEPTION, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<List<ProductPlaceResponseDTO>> getProductHistoryByProductId(@PathVariable("product_id") Long productId) {

        List<ProductPlaceResponseDTO> productPlaceResponseDTOS = processor.getProductHistoryByProductId(productId);
        return new ResponseEntity<>(productPlaceResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/id/{product_history_id}")
    public ResponseEntity<ProductPlaceResponseDTO> getProductHistoryById(@PathVariable("product_history_id") Long productHistoryId) {
        ProductPlaceResponseDTO productPlaceResponseDTO = processor.getProductHistoryById(productHistoryId);
        return new ResponseEntity<>(productPlaceResponseDTO, HttpStatus.OK);
    }
}
