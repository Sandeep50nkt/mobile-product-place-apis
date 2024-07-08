package com.mobile.place.entity.converter;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(final LocalDateTime attribute) {
        return attribute != null ? Timestamp.valueOf(attribute) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(final Timestamp dbData) {
        return dbData != null ? dbData.toLocalDateTime() : null;

    }

}