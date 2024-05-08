package com.microservices.servicefactura.configuration;


import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Value("${config.webclient.customer.url}")
    private String urlServiceCustomer;
    @Value("${config.webclient.product.url}")
    private String urlServiceProduct;

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
//    @Bean("customer")
//    public WebClient WebClientCustomer(){
//        return WebClient.create(urlServiceCustomer);
//    }
    @Bean("customer")
    public WebClient webClientCustomer(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(urlServiceCustomer).build();
    }
    @Bean("product")
    public WebClient webClientProduct(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(urlServiceProduct).build();
    }

    /**
     * CONFIGURACION DE CIRCUITBREAKER
     * **/
    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // Umbral de tasa de falla
                .waitDurationInOpenState(Duration.ofSeconds(5)) // Tiempo de espera en estado abierto
                .slidingWindowSize(5) // Tamaño de la ventana deslizante
                .build();
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(CircuitBreakerConfig circuitBreakerConfig) {
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
        registry.addConfiguration("customerService", circuitBreakerConfig);
        registry.addConfiguration("productService", circuitBreakerConfig);

        return registry;
    }

}
