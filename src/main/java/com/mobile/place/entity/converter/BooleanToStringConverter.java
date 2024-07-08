package com.mobile.place.entity.converter;

import javax.persistence.AttributeConverter;

public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(final Boolean attribute) {
        return attribute != null ? String.valueOf(attribute) : "false";
    }

    @Override
    public Boolean convertToEntityAttribute(final String dbData) {
        return dbData != null ? Boolean.valueOf(dbData) : false;

    }

}