# chargily-epay-springboot

Spring Boot Library for Chargily ePay Gateway

![Chargily ePay Gateway](https://raw.githubusercontent.com/Chargily/epay-gateway-php/main/assets/banner-1544x500.png "Chargily ePay Gateway")

# How to use

To use this library, there are two ways to configure keys and secret

- #### By providing your own configuration class like this

```
import ChargilyEpayClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ChargilyConfigParams.*;

@Configuration
    public class ChargilyEpayConfiguration {

        @Bean
        public ChargilyEpayClientConfig configureChargily(){
        ChargilyEpayClientConfig chargilyEpayClientConfig = new ChargilyEpayClientConfig();
        chargilyEpayClientConfig.put(BASE_URL, "https://epay.chargily.com.dz");
        chargilyEpayClientConfig.put(API_KEY, "your_api_key");
        chargilyEpayClientConfig.put(SECRET, "your_secret");
        return chargilyEpayClientConfig;
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
private ChargilyEpayClient client;

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
                //in case of failure
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //in case of success
            }
        }););
}
}
```