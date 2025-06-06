// br.com.conversor.moedas.config.AppConfig.java
package br.com.conversor.moedas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Indica que esta classe é uma fonte de definições de beans
public class AppConfig {

    @Bean // Publica uma instância de RestTemplate no contexto do Spring
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
