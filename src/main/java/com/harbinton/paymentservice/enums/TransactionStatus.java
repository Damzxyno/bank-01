package com.harbinton.paymentservice.enums;

public enum TransactionStatus {
    PENDING ("Pending"),
    DONE ("Done"),
    CANCELLED ("Cancelled"),
    FAILED ("Failed");

    private final String status;

    TransactionStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
