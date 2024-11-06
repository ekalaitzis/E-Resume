package dev.manos.E_Resume.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Collections;

@Configuration
public class RestClientConfig {

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> {
                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .build();
    }
}
