package com.microservices.apigateway.configuration.client;



import reactor.core.publisher.Mono;

import java.util.Map;

public interface AuthWebClient {
    Mono<Map<String, String>> validarToken(String token);
}
