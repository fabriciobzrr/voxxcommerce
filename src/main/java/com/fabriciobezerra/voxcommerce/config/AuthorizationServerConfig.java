package com.fabriciobezerra.voxcommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {

    @Value("${security.client-id}")
    private String clientId; // Pega o id do cliente no arquivo .properties

    @Value("${security.client-secret}")
    private String clientSecret; // Pega a senha do cliente no arquivo .properties

    @Value("${security.jwt.duration}")
    private Long jwtDuration; // Pega a duração da sessão no arquivo .properties

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Transforma a senha num código seguro e irrecuperável
    }

    @Bean
    public TokenSettings tokenSettings() {
        // Configura quanto tempo o token jwt fica válido em segundos
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofSeconds(jwtDuration))
                .build();
    }

    @Bean
    public ClientSettings clientSettings() {
        // Desativa a tela de confirmação, pois em API REST não usamos tela de navegador perguntando se desejamos autorizar o app
        return ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString()) // Gera um ID aleatório único no sistema
                .clientId(clientId) // Define o ID do cliente
                .clientSecret(passwordEncoder().encode(clientSecret)) // Criptografa a senha do cliente num código seguro e irreversível
                .scope("read") // Dá permissão de leitura
                .scope("write")// Dá permissão de escrita
                .authorizationGrantType(new AuthorizationGrantType("password")) // Permite o login com usuário e senha
                .tokenSettings(tokenSettings()) // Aplica o tempo de validade do duration com o método tokenSettings()
                .clientSettings(clientSettings()) // Aplica a regra de desativar a tela de confirmação com o método clientSettings()
                .build(); // Compila tudo
        // Salva esse cadastro na memória RAM para consulta rápida
        return new InMemoryRegisteredClientRepository(registeredClient);
    }
}
