package chargily.epay.springboot.service;

import chargily.epay.springboot.config.ChargilyClientConfig;
import chargily.epay.springboot.config.ChargilyConfigParams;
import chargily.epay.springboot.model.InvoiceModel;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public class ChargilyClient {
    private final ChargilyClientConfig chargilyClientConfig;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final MediaType APPLICATION_JSON = MediaType.parse("application/json");

    public ChargilyClient(ChargilyClientConfig chargilyClientConfig) {
        this.chargilyClientConfig = chargilyClientConfig;
    }

    /**
     * this is an async function to make a payment using chargily api
     * @param invoiceModel
     * @param responseCallback
     */
    public void makePayment(@Valid InvoiceModel invoiceModel, okhttp3.Callback responseCallback) {
        Gson gson = new Gson();
        String invoiceAsJson = gson.toJson(invoiceModel);
        RequestBody body = RequestBody.create(invoiceAsJson, APPLICATION_JSON);
        Request request = new Request.Builder()
                .url(chargilyClientConfig.getProperty(ChargilyConfigParams.BASE_URL) + "/api/invoice")
                .addHeader("X-Authorization", chargilyClientConfig.getProperty(ChargilyConfigParams.API_KEY))
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     *
     * @param signature
     * @param responseData
     * @return a boolean that represents the validity of the signature
     */
    public Boolean isSignatureValid(String signature, String responseData) {
        String secretKey = chargilyClientConfig.getProperty(ChargilyConfigParams.SECRET);
        String responseHash = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secretKey).hmacHex(responseData);
        return signature.equals(responseHash);
    }
}
