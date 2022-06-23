package com.harbinton.paymentservice.enums;

public enum URLS {
    TRANSACTION ("https://localhost:3000/harbinton-payment-gateway/");

    private final String uri;

    URLS(String uri){
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
