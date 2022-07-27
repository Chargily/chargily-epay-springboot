# chargily-epay-springboot

Spring Boot Library for Chargily ePay Gateway

# How to use

To use this library, there are two ways to configure keys and secret

- #### By providing your own configuration class like this

```
import ChargilyConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ChargilyConfigParams.*;

@Configuration
    public class ChargilyConfiguration {

        @Bean
        public ChargilyClientConfig configureChargily(){
        ChargilyClientConfig chargilyClientConfig = new ChargilyClientConfig();
        chargilyClientConfig.put(BASE_URL, "https://epay.chargily.com.dz");
        chargilyClientConfig.put(API_KEY, "your_api_key");
        chargilyClientConfig.put(SECRET, "your_secret");
        return chargilyClientConfig;
    }
}
```

- #### or simply adding by these properties on application.properties file

```
chargily.epay.apikey=your_api_key
chargily.epay.url=https://epay.chargily.com.dz
chargily.epay.secret=your_secret
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
    //handle response after you get it as a call back
    client.makePayment(invoice, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        }););
}
}
```