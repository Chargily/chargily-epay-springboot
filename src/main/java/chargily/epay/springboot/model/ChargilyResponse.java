package chargily.epay.springboot.model;

import com.google.gson.annotations.SerializedName;

public class ChargilyResponse {

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    @SerializedName("checkout_url")
    public String checkoutUrl;
}
