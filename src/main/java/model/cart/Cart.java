package model.cart;

import model.product.Product;
import model.product.Shippable;
import service.shipping.ShippableProd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {
    private Map<Product, Integer> products;
    public Cart() {
        this.products = new HashMap<>();
    }
    public Map<Product, Integer> getProducts() {
        return new HashMap<>(products);
    }
    public boolean add(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        int currentQuantity = products.getOrDefault(product, 0);
        int totalRequested = currentQuantity + quantity;
        if (!product.isAvailable(totalRequested)) {
            System.out.println("Cannot add " + quantity + " units of " + product.getName() +
                    " - insufficient stock or expired");
            return false;
        }
        products.put(product, totalRequested);
        System.out.println("Added " + quantity + " units of " + product.getName() + " to cart");
        return true;
    }

    public void remove(Product product) {
        products.remove(product);
    }
    public boolean isEmpty() {
        return products.isEmpty();
    }
    public double getSubtotal() {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public List<ShippableProd> getShippableItems() {
        return products.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Shippable)
                .map(entry -> new CartShippableProd(
                        entry.getKey().getName(),
                        ((Shippable) entry.getKey()).getWeight() * entry.getValue(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    public void processCheckout() {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            entry.getKey().decreaseQuantity(entry.getValue());
        }
        products.clear();
    }

    public void printCart() {
        System.out.println("\n=== CART CONTENTS ===");
        if (isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;

            System.out.printf("%dx %s - $%.2f each = $%.2f%n",
                    quantity, product.getName(), product.getPrice(), itemTotal);
        }

        System.out.printf("Cart Subtotal: $%.2f%n", getSubtotal());
        System.out.println("===================");
    }
}