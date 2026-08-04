package com.atlas.searchgateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379",
    "spring.kafka.bootstrap-servers=localhost:9092"
})
class SearchGatewayApplicationTest {

    @Test
    void contextLoads() {
    }
}
