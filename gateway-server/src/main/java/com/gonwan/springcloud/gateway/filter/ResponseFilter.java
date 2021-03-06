package com.gonwan.springcloud.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import brave.Tracer;
import reactor.core.publisher.Mono;

@Component
public class ResponseFilter implements GlobalFilter {

    private static final String CORRELATION_ID = "sc-correlation-id";

    @Autowired
    private Tracer tracer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add(CORRELATION_ID, tracer.currentSpan().context().traceIdString());
        }));
    }

}
