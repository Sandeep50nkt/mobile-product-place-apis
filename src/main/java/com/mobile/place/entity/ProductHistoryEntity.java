package com.mobile.place.entity;

import com.mobile.place.entity.converter.LocalDateTimeConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product_history_info")
public class ProductHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", updatable = false)
    private @NotNull Long productId;

    @Column(name = "user_id", updatable = false)
    private @NotNull String userId;

    @Column(name = "status", updatable = false)
    private @NotNull String status;

    @CreationTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "CREATE_TIMESTAMP", updatable = false)
    private LocalDateTime createTimestamp;
}
