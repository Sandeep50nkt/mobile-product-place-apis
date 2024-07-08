package com.mobile.place.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.findAndRegisterModules();
    }

    @SneakyThrows
    public static <T> String toMessageJson(T dto) {
        return objectMapper.writeValueAsString(dto);
    }

}