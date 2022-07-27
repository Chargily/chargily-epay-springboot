package chargily.epay.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "epay.chargily")
public class ChargilyProperties {
    private String url;
    private String apikey;
    private String secret;
}
