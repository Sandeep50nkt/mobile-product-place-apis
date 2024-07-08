package com.mobile.place.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResponseDTO {
    private Long productId;
    private @NotNull String productName;
    private @NotNull String imageURL;
    private @NotNull String description;
    private @NotNull String brand;
    private @NotNull String userId;
    private @NotNull Boolean isAvailable;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private @NotNull LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private @NotNull LocalDateTime updatedTime;
}
