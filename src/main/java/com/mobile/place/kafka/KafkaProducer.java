package com.mobile.place.kafka;

import com.mobile.place.common.LogFormat;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaProducer {
    private static final String LOG_OP_INFO = "KafkaProducer";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String key, String topic, String message, String sessionId) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
        future.completable().whenComplete((result, ex) -> {
            if (ex != null) {
                MDC.put(LogFormat.MDC_SESSIONID, sessionId);
                log.error(LogFormat.error(LOG_OP_INFO,
                        MessageFormat.format("unable to send message={0}", message),
                        new Exception(ex)), ex);
            } else {
                MDC.put(LogFormat.MDC_SESSIONID, sessionId);
                log.info(LogFormat.LOGGER_OK_PATTERN, LOG_OP_INFO, MessageFormat
                        .format("Payload={0} sent to topic={1}, with offset={2} and partition={3}",
                                message, topic, result.getRecordMetadata().offset(),
                                result.getRecordMetadata().partition()));
            }
        });
    }

}