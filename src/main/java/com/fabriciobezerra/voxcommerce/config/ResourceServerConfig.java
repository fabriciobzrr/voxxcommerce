package com.fabriciobezerra.voxcommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 1. Desativa a proteção CSRF - APIs REST não usam sessão de navegador (são stateless), por isso desativamos essa proteção
        http.csrf(csrf -> csrf.disable());

        // 2. Define as regras de quem pode acessar cada caminho (endpoint)
        http.authorizeHttpRequests(authorize -> authorize
                        // Libera o console do banco H2 para desenvolvimento
//                .requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        // Exemplo de rota pública (descomentar se precisar liberar leitura sem login) - .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        // Exige que qualquer outra requisição na API esteja autenticada

                        // Em conjunto com o .anyRequest() podemos utilizar o:
                        // .permitAll() que permite acessar o endpoint de qualquer requisição
                        // .authenticated() que permite somente acessar o endpoint quando estiver autenticado
                        .anyRequest().permitAll()
        );

        // 3. Permite exibir as telas do H2 Console no navegador (desativa bloqueio de frames)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        // 4. Configura a API para validar tokens JWT automaticamente quando chegarem no cabeçalho
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
        }));

        // 5. Constrói e devolve a corrente de filtros pronta para o Spring usar
        return http.build();
    }
}
