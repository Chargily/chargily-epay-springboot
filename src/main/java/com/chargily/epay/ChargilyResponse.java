package com.chargily.epay;

import java.io.Serializable;

public class ChargilyResponse implements Serializable {

    private String checkout_url;

    public ChargilyResponse() {
    }

    public ChargilyResponse(String checkout_url) {
        this.checkout_url = checkout_url;
    }

    public String getCheckout_url() {
        return checkout_url;
    }

    public void setCheckout_url(String checkout_url) {
        this.checkout_url = checkout_url;
    }

    @Override
    public String toString() {
        return "ChargilyResponse{" +
                "checkout_url='" + checkout_url + '\'' +
                '}';
    }
}
