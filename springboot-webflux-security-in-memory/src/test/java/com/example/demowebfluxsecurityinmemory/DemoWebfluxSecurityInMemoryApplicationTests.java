package com.example.demowebfluxsecurityinmemory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;
import java.util.function.Consumer;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.Credentials.basicAuthenticationCredentials;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoWebfluxSecurityInMemoryApplicationTests {

    @Autowired
    ApplicationContext context;

    WebTestClient rest;

    @Before
    public void setup() {
        this.rest = WebTestClient
            .bindToApplicationContext(this.context)
            .apply(springSecurity())
            .configureClient()
            .filter(basicAuthentication())
            .build();
    }

    @Test
    public void basicWhenNoCredentialsThenUnauthorized() throws Exception {
        this.rest
            .get()
            .uri("/")
            .exchange()
            .expectStatus().isUnauthorized();
    }

    @Test
    public void basicWhenValidCredentialsThenOk() throws Exception {
        this.rest
            .get()
            .uri("/")
            .attributes(userCredentials())
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("{\"message\":\"Hello user!\"}");
    }

    @Test
    public void basicWhenInvalidCredentialsThenUnauthorized() throws Exception {
        this.rest
            .get()
            .uri("/")
            .attributes(invalidCredentials())
            .exchange()
            .expectStatus().isUnauthorized()
            .expectBody().isEmpty();
    }

    @Test
    public void mockSupportWhenMutateWithMockUserThenOk() throws Exception {
        this.rest
            .mutateWith(mockUser())
            .get()
            .uri("/")
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("{\"message\":\"Hello user!\"}");
    }

    @Test
    @WithMockUser
    public void mockSupportWhenWithMockUserThenOk() throws Exception {
        this.rest
            .get()
            .uri("/")
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("{\"message\":\"Hello user!\"}");
    }

    private Consumer<Map<String, Object>> userCredentials() {
        return basicAuthenticationCredentials("user", "user");
    }

    private Consumer<Map<String, Object>> invalidCredentials() {
        return basicAuthenticationCredentials("user", "INVALID");
    }
}
