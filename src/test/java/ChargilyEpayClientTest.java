import chargily.epay.springboot.config.ChargilyEpayClientConfig;
import chargily.epay.springboot.enums.Mode;
import chargily.epay.springboot.model.ChargilyResponse;
import chargily.epay.springboot.model.InvoiceModel;
import chargily.epay.springboot.service.ChargilyEpayClient;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class ChargilyEpayClientTest {

    private static ChargilyEpayClient chargilyEpayClient;
    private boolean isResponse = false;
    private ChargilyResponse responseClient;
    private int responseStatusCode;
    private final int HTTP_STATUS_CREATED = 201;

    @BeforeAll
    static void init() {
        ChargilyEpayClientConfig chargilyEpayClientConfig = new ChargilyEpayClientConfig();
        chargilyEpayClientConfig.setProperty("url", "https://epay.chargily.com.dz");
        chargilyEpayClientConfig.setProperty("apikey", "api_KFWtdBczv0qnAMHNxGXCGVK93yEZahZwr4EgFa4xmfnLTIJkezPvW0LgqholrC7S");
        chargilyEpayClient = new ChargilyEpayClient(chargilyEpayClientConfig);
    }

    @Test
    void should_make_payment() {
        // Given
        InvoiceModel invoiceModel = InvoiceModel.builder()
                .client("Chakhoum Ahmed")
                .client_email("rainxh11@gmail.com")
                .discount(5d)
                .webhook_url("https://backend.com/webhook_endpoint")
                .back_url("https://frontend.com")
                .mode(Mode.EDAHABIA)
                .invoice_number("1")
                .amount(BigDecimal.valueOf(10000))
                .comment("sending in progress")
                .build();

        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                isResponse = true;
                responseStatusCode = response.code();

                Gson gson = new Gson();
                ResponseBody responseBody = response.body();

                if (responseBody != null) {
                    responseClient = gson.fromJson(responseBody.string(), ChargilyResponse.class);
                }
            }

        };

        // When
        chargilyEpayClient.makePayment(invoiceModel, responseCallback);

        // Then
        await().until(() -> isResponse);
        assertThat(responseStatusCode).isEqualTo(HTTP_STATUS_CREATED);
        assertThat(responseClient.checkoutUrl).contains("https://epay.chargily.com.dz/checkout/");
    }
}