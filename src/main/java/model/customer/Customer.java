package model.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.cart.Cart;

@AllArgsConstructor
@Getter
public class Customer {
    private Cart cart;
    private Double balance;
    public void deductBalance(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }
}
