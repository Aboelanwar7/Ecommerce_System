package model.product.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.product.Expirable;
import model.product.Product;
import model.product.Shippable;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
public class Cheese implements Product, Expirable, Shippable {
    private String name;
    private double price;
    private int quantity;
    private LocalDate expiryDate;
    private double weight;

    @Override
    public void decreaseQuantity(int amount) {
        if(amount <= quantity) {
            this.quantity -= amount;
        } else {
            throw new IllegalArgumentException("Not enough quantity available");
        }
    }

    @Override
    public boolean isAvailable(int amount) {
        return amount <= quantity && !isExpired();
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
}
