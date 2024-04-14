package com.example.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

@Component
public class UserIdHeaderFilter implements GlobalFilter {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";


    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyBlEkbQIGE9ziS43Ts71aLbceNBlXwzdHdZKhau/51NhuvxYS9ssjJ9/nyoINZiDA4Fzpngtw22HOLYZ0cSZfb8BXmQIDyUXAdqiQjZ0YT1jq03po0M4mEda+vHnyLVBtZGvC97f3cVtgIiU85P+D3r2tf8YE41020vvoPb0pb/1kSQZy4XE+/5jocS0HV1VLjcchTaGqKulLj0psRnUR3SzIkpW1/2JQDbv0yS8aSDOF8qpEolrvGKgGBYVpo4V26gLqMxqcDni5LfwayGKko/PQKFtedAERnssJHDuwN7ckBiAKQuQR3pRplrPR3W7LUFenNX4CCXoKkqSq8wm3wIDAQAB";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (request.getHeaders().containsKey(HEADER_AUTHORIZATION)) {
            String token = Objects.requireNonNull(request.getHeaders().getFirst(HEADER_AUTHORIZATION)).substring(BEARER_PREFIX.length());
            try {
                String userId = extractUserIdFromToken(token);
                if (userId != null) {
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("userId", userId)
                            .build();
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                }
            } catch (JwtException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return chain.filter(exchange);
    }

    private String extractUserIdFromToken(String token) throws Exception {
        PublicKey publicKey = convertStringToPublicKey();

        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
        return jwsClaims.getBody().getSubject();
    }

    private PublicKey convertStringToPublicKey() throws Exception {
        byte[] publicBytes = Base64.getDecoder().decode(UserIdHeaderFilter.PUBLIC_KEY);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
