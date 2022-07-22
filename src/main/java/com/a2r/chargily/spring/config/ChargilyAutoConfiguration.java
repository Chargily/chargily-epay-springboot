package com.a2r.chargily.spring.config;

import static com.a2r.chargily.spring.config.ChargilyConfigParams.*;

import com.a2r.chargily.spring.service.ChargilyClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ChargilyClient.class)
@EnableConfigurationProperties(ChargilyProperties.class)
public class ChargilyAutoConfiguration {

    private final ChargilyProperties chargilyProperties;

    public ChargilyAutoConfiguration(ChargilyProperties chargilyProperties) {
        this.chargilyProperties = chargilyProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ChargilyConfig greeterConfig() {

        String apikey = chargilyProperties.getApikey() == null
                ? System.getProperty("apikey")
                : chargilyProperties.getApikey();
        String chargilyURL = chargilyProperties.getUrl() == null
                ? System.getProperty("url")
                : chargilyProperties.getUrl();
        String secret = chargilyProperties.getSecret() == null
                ? System.getProperty("secret")
                : chargilyProperties.getSecret();

        ChargilyConfig chargilyConfig = new ChargilyConfig();

        chargilyConfig.put(API_KEY, apikey);
        chargilyConfig.put(BASE_URL, chargilyURL);
        chargilyConfig.put(SECRET, secret);

        return chargilyConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ChargilyClient greeter(ChargilyConfig greetingConfig) {
        return new ChargilyClient(greetingConfig);
    }
}
