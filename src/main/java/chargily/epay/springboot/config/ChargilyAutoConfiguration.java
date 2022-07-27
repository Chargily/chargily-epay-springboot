package chargily.epay.springboot.config;

import static chargily.epay.springboot.config.ChargilyConfigParams.*;

import chargily.epay.springboot.service.ChargilyClient;
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
    public ChargilyClientConfig chargilyClientConfig() {

        String apikey = chargilyProperties.getApikey() == null
                ? System.getProperty("apikey")
                : chargilyProperties.getApikey();
        String chargilyURL = chargilyProperties.getUrl() == null
                ? System.getProperty("url")
                : chargilyProperties.getUrl();
        String secret = chargilyProperties.getSecret() == null
                ? System.getProperty("secret")
                : chargilyProperties.getSecret();

        ChargilyClientConfig chargilyClientConfig = new ChargilyClientConfig();

        chargilyClientConfig.put(API_KEY, apikey);
        chargilyClientConfig.put(BASE_URL, chargilyURL);
        chargilyClientConfig.put(SECRET, secret);

        return chargilyClientConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ChargilyClient chargilyClient(ChargilyClientConfig chargilyClientConfig) {
        return new ChargilyClient(chargilyClientConfig);
    }
}
