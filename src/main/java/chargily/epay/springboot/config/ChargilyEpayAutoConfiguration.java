package chargily.epay.springboot.config;

import static chargily.epay.springboot.config.ChargilyEpayConfigParams.*;

import chargily.epay.springboot.service.ChargilyEpayClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ChargilyEpayClient.class)
@EnableConfigurationProperties(ChargilyEpayProperties.class)
public class ChargilyEpayAutoConfiguration {

    private final ChargilyEpayProperties chargilyEpayProperties;

    public ChargilyEpayAutoConfiguration(ChargilyEpayProperties chargilyEpayProperties) {
        this.chargilyEpayProperties = chargilyEpayProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ChargilyEpayClientConfig chargilyClientConfig() {

        String apikey = chargilyEpayProperties.getApikey() == null
                ? System.getProperty("apikey")
                : chargilyEpayProperties.getApikey();
        String chargilyURL = chargilyEpayProperties.getUrl() == null
                ? System.getProperty("url")
                : chargilyEpayProperties.getUrl();
        String secret = chargilyEpayProperties.getSecret() == null
                ? System.getProperty("secret")
                : chargilyEpayProperties.getSecret();

        ChargilyEpayClientConfig chargilyEpayClientConfig = new ChargilyEpayClientConfig();

        chargilyEpayClientConfig.put(API_KEY, apikey);
        chargilyEpayClientConfig.put(BASE_URL, chargilyURL);
        chargilyEpayClientConfig.put(SECRET, secret);

        return chargilyEpayClientConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ChargilyEpayClient chargilyClient(ChargilyEpayClientConfig chargilyEpayClientConfig) {
        return new ChargilyEpayClient(chargilyEpayClientConfig);
    }
}
