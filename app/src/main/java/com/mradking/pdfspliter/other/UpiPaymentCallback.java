package com.mradking.pdfspliter.other;

public interface UpiPaymentCallback {

    void onUpiPaymentSuccess(String transactionId);
    void onUpiPaymentFailure(String errorMessage);
}
