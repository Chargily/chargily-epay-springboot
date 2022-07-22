package com.chargily.epay;


import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

public class Invoice implements Serializable {

    static public enum PaymentMode implements Serializable {
        EDAHABIA,CIB
    }


    @NotNull
    @Size(min = 3 , message = "client name should be atleast 3 charaters long")
    private String client;

    @NotNull
    @Email(message = "not a valid email")
    private String client_email;

    @NotNull
    private String invoice_number;

    @NotNull
    @DecimalMin(value = "75.0" , message = "amount should be atleast 75")
    private Double amount;

    @NotNull
    @Min(value = 0, message = "discount percentage should be atleast 0%")
    @Max(value = 100 , message = "discount percentage can't be more than 99.99%")
    private Double discount;

    @NotNull
    @URL
    private String back_url;

    @NotNull
    @URL
    private String webhook_url;

    @NotNull
    private PaymentMode mode;

    @NotNull
    private String comment;


    public Invoice(String client, String client_email, String invoice_number, Double amount, Double discount, String back_url, String webhook_url, PaymentMode mode, String comment) {
        this.client = client;
        this.client_email = client_email;
        this.invoice_number = invoice_number;
        this.amount = amount;
        this.discount = discount;
        this.back_url = back_url;
        this.webhook_url = webhook_url;
        this.mode = mode;
        this.comment = comment;
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getBack_url() {
        return back_url;
    }

    public void setBack_url(String back_url) {
        this.back_url = back_url;
    }

    public String getWebhook_url() {
        return webhook_url;
    }

    public void setWebhook_url(String webhook_url) {
        this.webhook_url = webhook_url;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public void setMode(PaymentMode mode) {
        this.mode = mode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



}

