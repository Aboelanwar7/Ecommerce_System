package model.product.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.product.Product;

@Getter
@AllArgsConstructor
public class ScratchCard implements Product {
    private String name;
    private double price;
    private int quantity;

    @Override
    public void decreaseQuantity(int amount) {
        quantity -= amount;
    }

    @Override
    public boolean isAvailable(int amount) {
        return amount <= quantity;
    }
}
