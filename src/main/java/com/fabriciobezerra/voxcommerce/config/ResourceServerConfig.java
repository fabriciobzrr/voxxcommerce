package com.fabriciobezerra.voxcommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerConfig {

    @Value("${cors.origins}")
    // Injeta as origens permitidas para CORS vindas do arquivo application.properties.
    private String corsOrigins;

    @Bean
    @Profile("test")
    @Order(1)
    public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
        // Libera o acesso ao painel do banco H2 apenas quando o sistema estiver rodando no perfil de testes.
        http.securityMatcher(PathRequest.toH2Console()).csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain rsSecurityFilterChain(HttpSecurity http) throws Exception {
        // Desliga a proteção contra CSRF, que não é necessária em APIs que usam token.
        http.csrf(csrf -> csrf.disable());

        // Deixa a checagem fina de permissão para ser feita nas rotas (Controllers).
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

        // Configura a API para aceitar e validar os tokens JWT recebidos.
        http.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));

        // Ativa as regras de liberação de acesso externo (CORS).
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Aponta qual é o campo dentro do token que guarda os papéis/permissões do usuário.
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        // Garante que nenhum prefixo extra seja adicionado antes das permissões.
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        // Junta a regra de leitura das permissões para dentro do conversor principal.
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        // Separa os endereços permitidos caso tenha mais de um na lista.
        String[] origins = corsOrigins.split(",");

        CorsConfiguration corsConfig = new CorsConfiguration();

        // Define quais sites ou front-ends externos podem conversar com a API.
        corsConfig.setAllowedOriginPatterns(Arrays.asList(origins));

        // Libera quais ações HTTP são permitidas.
        corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));

        // Permite o envio de credenciais e cabeçalhos de autenticação nas requisições.
        corsConfig.setAllowCredentials(true);

        // Libera os cabeçalhos que a aplicação pode receber.
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Aplica essas regras para todas as rotas da API.
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> filterRegistrationBeanCorsFilter() {
        // Coloca o filtro de CORS para rodar com prioridade máxima antes de qualquer outra validação.
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
