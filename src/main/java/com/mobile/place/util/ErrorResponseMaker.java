package com.mobile.place.util;

import com.mobile.place.dto.response.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseMaker {
    public static ErrorResponse getErrorObject(String errorMessage, String errorDetails) {
        List<String> errorDetail = new ArrayList<>();
        errorDetail.add(errorDetails);
        return new ErrorResponse(errorMessage, errorDetail);
    }
}
