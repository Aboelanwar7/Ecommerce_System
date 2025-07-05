package model.product;

public interface Product {
    String getName();
    double getPrice();
    int getQuantity();
    void decreaseQuantity(int amount);
    boolean isAvailable(int amount);
}
