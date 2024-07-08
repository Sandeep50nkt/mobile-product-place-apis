package com.mobile.place.validator;

import com.mobile.place.exceptions.InvalidDataRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;

@Slf4j
public class ValidationErrorParser {

    private static final Map<String, String> ERROR_CODES_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
        {
            put("NotNull.productPlaceRequestDTO.userId", "_ERR_USER_ID_CANNOT_BE_BLANK");
            put("NotNull.productPlaceRequestDTO.status", "_ERR_STATUS_CANNOT_BE_BLANK");
            put("NotNull.productPlaceRequestDTO.invalidStatus", "_ERR_INVALID_STATUS");

        }
    });

    public static void processErrors(BindingResult errors) {
        if (null != errors) {
            List<String> errorCodes = new ArrayList<String>();
            for (FieldError error : errors.getFieldErrors()) {
                String errorKey = error.getCodes()[1];
                errorCodes.add(ERROR_CODES_MAP.get(errorKey));
                log.info("op={}, status={} , desc=validation error with errorKey : {}",
                        "VALIDATION_ERRORS", "STATUS_NOT_OK", errorKey);
            }

            for (ObjectError error : errors.getGlobalErrors()) {
                String errorKey = error.getCodes()[1];
                errorCodes.add(ERROR_CODES_MAP.get(errorKey));
                log.info("op={}, status={} , desc=validation error with errorKey : {}",
                        "VALIDATION_ERRORS", "STATUS_NOT_OK", errorKey);
            }
            if (errorCodes.size() > 0) {
                throw new InvalidDataRequestException(errorCodes.toString());

            }
        }
    }

}
