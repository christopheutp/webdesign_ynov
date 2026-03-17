package org.example.exercice10;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
               // .csrf().disable()
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
               // .authorizeExchange()
               // .pathMatchers("/api/rooms").hasAnyRole("USER","ADMIN")
              //  .pathMatchers("/api/rooms/**").hasRole("ADMIN")
              //  .anyExchange().authenticated()
               // .and()
                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/api/rooms").hasAnyRole("USER","ADMIN")
                                .pathMatchers("/api/rooms/**").hasRole("ADMIN")
                                .anyExchange().authenticated()
                )
               // .httpBasic()
                .httpBasic().authenticationEntryPoint(unauthorizedEntryPoint())//  authentification HTTP
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()) // gestionnaire personalise pour les erreurs 403
                .and().build();
    }


    @Bean
    public ServerAuthenticationEntryPoint unauthorizedEntryPoint() {
        return (exchange, ex) -> Mono.fromRunnable(() -> {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String body = "{\"error\": \"UNAUTHORIZED\", \"message\": \"Vous devez vous connecter pour accéder à cette ressource\"}";
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))).subscribe();
        });
    }

    @Bean
    public ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> Mono.fromRunnable(() -> {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String body = "{\"error\": \"FORBIDDEN\", \"message\": \"Vous n'avez pas les droits nécessaires pour accéder à cette ressource\"}";
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))).subscribe();
        });
    }
}
