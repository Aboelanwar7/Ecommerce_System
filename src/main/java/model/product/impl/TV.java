package model.product.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.product.Product;
import model.product.Shippable;

@Getter
@AllArgsConstructor
public class TV implements Product, Shippable {
    private String name;
    private double price;
    private int quantity;
    private double weight;

    @Override
    public void decreaseQuantity(int amount) {
        quantity -= amount;
    }

    @Override
    public boolean isAvailable(int amount) {
        return amount <= quantity;
    }
}
