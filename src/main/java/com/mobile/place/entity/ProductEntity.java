package com.mobile.place.entity;

import com.mobile.place.entity.converter.BooleanToStringConverter;
import com.mobile.place.entity.converter.LocalDateTimeConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", updatable = false)
    private @NotNull String name;

    @Column(name = "image_url", updatable = false)
    private @NotNull String imageURL;

    @Column(name = "description", updatable = false)
    private @NotNull String description;

    @Column(name = "brand", updatable = false)
    private @NotNull String brand;

    @Column(name = "modified_by")
    private @NotNull String modifiedBy;

    @Convert(converter = BooleanToStringConverter.class)
    @Column(name = "available")
    private @NotNull Boolean isAvailable;

    @CreationTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "CREATE_TIMESTAMP", updatable = false)
    private LocalDateTime createTimestamp;

    @UpdateTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "MODIFIED_TIMESTAMP")
    private LocalDateTime modifiedTimestamp;

    @Version
    @Column(name = "VERSION")
    private Integer version = 0;
}
