package model.checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckoutResult {
    private double subtotal;
    private double shippingFee;
    private double total;
    private double customerBalanceAfter;
}