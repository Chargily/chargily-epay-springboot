package chargily.epay.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "chargily.epay")
public class ChargilyEpayProperties {
    private String url;
    private String apikey;
    private String secret;
}
