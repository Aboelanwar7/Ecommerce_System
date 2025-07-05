package model.cart;

import lombok.AllArgsConstructor;
import service.shipping.ShippableProd;
@AllArgsConstructor
public class CartShippableProd implements ShippableProd {
    private String name;
    private double totalWeight;
    private int quantity;

    @Override
    public String getName() {
        return quantity + "x " + name;
    }

    @Override
    public double getWeight() {
        return totalWeight;
    }
}
