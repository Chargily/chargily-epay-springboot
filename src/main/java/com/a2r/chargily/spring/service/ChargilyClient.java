package com.a2r.chargily.spring.service;

import com.a2r.chargily.spring.config.ChargilyConfig;
import com.a2r.chargily.spring.model.InvoiceModel;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import static com.a2r.chargily.spring.config.ChargilyConfigParams.*;

import java.io.IOException;
@Validated
public class ChargilyClient {
    private final ChargilyConfig chargilyConfig;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final MediaType APPLICATION_JSON = MediaType.parse("application/json");

    public ChargilyClient(ChargilyConfig chargilyConfig) {
        this.chargilyConfig = chargilyConfig;
    }

    public Response makePayment(@Valid InvoiceModel invoiceModel) throws IOException {
        Gson gson = new Gson();
        String invoiceAsJson = gson.toJson(invoiceModel);
        RequestBody body = RequestBody.create(invoiceAsJson, APPLICATION_JSON);
        System.out.println(chargilyConfig.getProperty(BASE_URL));
        Request request = new Request.Builder()
                .url(chargilyConfig.getProperty(BASE_URL) + "/api/invoice")
                .addHeader("X-Authorization", chargilyConfig.getProperty(API_KEY))
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        return call.execute();
    }
    public Boolean isSignatureValid(String signature, String responseData){
        String secretKey = chargilyConfig.getProperty(SECRET);
        String responseHash = new HmacUtils(HmacAlgorithms.HMAC_SHA_256,secretKey).hmacHex(responseData);
        return signature.equals(responseHash);
    }
}
