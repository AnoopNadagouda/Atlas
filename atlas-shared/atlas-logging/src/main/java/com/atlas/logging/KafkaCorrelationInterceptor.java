package com.atlas.logging;

import com.atlas.common.utils.AtlasConstants;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KafkaCorrelationInterceptor<K, V> implements ProducerInterceptor<K, V> {

    @Override
    public ProducerRecord<K, V> onSend(ProducerRecord<K, V> record) {
        String correlationId = CorrelationIdContext.getCorrelationId();
        if (record.headers().lastHeader(AtlasConstants.CORRELATION_ID_HEADER) == null) {
            record.headers().add(AtlasConstants.CORRELATION_ID_HEADER, correlationId.getBytes(StandardCharsets.UTF_8));
        }
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {}

    @Override
    public void close() {}

    @Override
    public void configure(Map<String, ?> configs) {}

    public static String extractCorrelationId(org.apache.kafka.clients.consumer.ConsumerRecord<?, ?> record) {
        Header header = record.headers().lastHeader(AtlasConstants.CORRELATION_ID_HEADER);
        if (header != null && header.value() != null) {
            return new String(header.value(), StandardCharsets.UTF_8);
        }
        return CorrelationIdContext.generateNewCorrelationId();
    }
}
