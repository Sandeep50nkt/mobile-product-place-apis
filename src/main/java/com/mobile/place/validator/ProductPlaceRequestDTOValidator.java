package com.mobile.place.validator;

import com.mobile.place.common.Status;
import com.mobile.place.dto.request.ProductPlaceRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component("ProductPlaceRequestDTOValidator")
public class ProductPlaceRequestDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductPlaceRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductPlaceRequestDTO productPlaceRequestDTO = (ProductPlaceRequestDTO) target;
        if (StringUtils.isBlank(productPlaceRequestDTO.getStatus())) {
            errors.reject("NotNull.productPlaceRequestDTO.status");
        } else {
            Optional<Status> statusOptional = Status.getStatus(productPlaceRequestDTO.getStatus());
            if (statusOptional.isEmpty()) {
                errors.reject("NotNull.productPlaceRequestDTO.invalidStatus");
            }
        }

        if (StringUtils.isBlank(productPlaceRequestDTO.getUserId()))
            errors.reject("NotNull.productPlaceRequestDTO.userId");


    }
}