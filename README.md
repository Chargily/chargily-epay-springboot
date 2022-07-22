# chargily-epay-springboot

Spring Boot Library for Chargily ePay Gateway

# How to use

To use this library, there are two ways to configure keys and secret

- #### By providing your own configuration class like this

```
import com.a2r.chargily.spring.config.ChargilyConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.a2r.chargily.spring.config.ChargilyConfigParams.*;

@Configuration
    public class ChargilyConfiguration {

        @Bean
        public ChargilyConfig configureChargily(){
        ChargilyConfig chargilyConfig = new ChargilyConfig();
        chargilyConfig.put(BASE_URL, "https://epay.chargily.com.dz");
        chargilyConfig.put(API_KEY, "your_api_key");
        chargilyConfig.put(SECRET, "your_secret");
        return chargilyConfig;
    }
}
```

- #### or simply adding by these properties on application.properties file

```
epay.chargily.apikey=your_api_key
epay.chargily.url=https://epay.chargily.com.dz
epay.chargily.secret=your_secret
```

#### then to make a payment simply inject the ChargilyClient in your service

either by constructor or field injection like this (constructor injection is preferred, but I will use field injection
just for demo)

```
public class MyService{
@Autowired
private ChargilyClient client

public void makePayment(){
    InvoiceModel invoice = new InvoiceModel(
                "someClient",
                "someEmail@mail.com",
                "1000",
                BigDecimal.valueOf(75.0),
                55d,
                "https://backurl.com/",
                "https://webhookurl.com/",
                Mode.CIB,
                "a comment"
                );
    //handle response after you get it 
    Response response = client.makePayment(invoice);
}
}
```