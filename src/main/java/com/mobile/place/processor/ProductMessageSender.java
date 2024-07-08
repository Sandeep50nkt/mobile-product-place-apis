package com.mobile.place.processor;

import com.mobile.place.common.LogFormat;
import com.mobile.place.dto.response.ProductPlaceAPIResponseDTO;
import com.mobile.place.kafka.KafkaProducer;
import com.mobile.place.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Slf4j
@Component
public class ProductMessageSender {
    private static final String LOG_OP_INFO = "ProductMessageSender";

    @Value("${product.kafka.enable}")
    private boolean enable;

    @Value("${product.kafka.topic}")
    private String topic;

    private final KafkaProducer kafkaProducer;

    @Autowired
    ProductMessageSender(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Async
    public void sendMessage(Long productId, ProductPlaceAPIResponseDTO productPlaceAPIResponseDTO, String sessionId) {
        if (!enable) {
            log.info(LogFormat.LOGGER_OK_PATTERN, LOG_OP_INFO, "sendMessage - kafka feed publish disabled");
            return;
        }

        if (productPlaceAPIResponseDTO.isSuccess()) {
            kafkaProducer.sendMessage(String.valueOf(productId),
                    topic,
                    JsonUtil.toMessageJson(productPlaceAPIResponseDTO.getResponseBody()),
                    sessionId);
        } else {
            log.info(LogFormat.LOGGER_OK_PATTERN, LOG_OP_INFO,
                    MessageFormat.format("sendMessage - not eligible for product_id={0}",
                            productId));

        }
    }
}
