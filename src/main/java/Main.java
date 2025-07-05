package main.java;

import model.cart.Cart;
import model.checkout.CheckoutResult;
import model.customer.Customer;
import model.product.impl.Cheese;
import model.product.impl.ScratchCard;
import model.product.impl.TV;
import service.checkout.CheckoutException;
import service.checkout.EcommerceSystem;

import java.time.LocalDate;
public class Main {
    public static void main(String[] args) {
        System.out.println("=== E-COMMERCE SYSTEM DEMO ===\n");
        EcommerceSystem ecommerceSystem = new EcommerceSystem();
        Cheese cheese = new Cheese("Cheese", 100.0, 10, LocalDate.now().plusDays(7), 0.2);
        TV tv = new TV("TV", 500.0, 3, 15.0);
        ScratchCard scratchCard = new ScratchCard("Mobile scratch card", 25.0, 20);
        Cart cart = new Cart();
        Customer customer = new Customer(cart, 10000.0);

        System.out.println("Initial customer balance: $" + customer.getBalance());
        System.out.println();
        System.out.println("=== ADDING ITEMS TO CART ===");
        cart.add(cheese, 2);
        cart.add(tv, 1);
        cart.add(scratchCard, 1);
        cart.printCart();

        System.out.println("\n=== CHECKOUT PROCESS ===");
        try {
            CheckoutResult result = ecommerceSystem.checkout(customer, cart);
            System.out.println("\nCheckout completed successfully!");
            System.out.printf("Customer balance after payment: $%.2f%n", result.getCustomerBalanceAfter());
        } catch (CheckoutException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }
        // Test error cases
        System.out.println("\n" + "=".repeat(50));
        testErrorCases(ecommerceSystem);
    }

    private static void testErrorCases(EcommerceSystem ecommerceSystem) {
        System.out.println("=== TESTING validations ===");
        // Test1: empty cart
        System.out.println("\n1. Testing empty cart:");
        Cart emptyCart = new Cart();
        Customer customer1 = new Customer(emptyCart, 1000.0);
        try {
            ecommerceSystem.checkout(customer1, emptyCart);
        } catch (CheckoutException e) {
            System.out.println("☑ " + e.getMessage());
        }

        // Test2: insufficient balance
        System.out.println("\n2. Testing insufficient balance:");
        Cart expensiveCart = new Cart();
        Customer poorCustomer = new Customer(expensiveCart, 50.0);
        TV expensiveTV = new TV("Expensive TV", 500.0, 3, 15.0);
        expensiveCart.add(expensiveTV, 1);
        try {
            ecommerceSystem.checkout(poorCustomer, expensiveCart);
        } catch (CheckoutException e) {
            System.out.println("☑ " + e.getMessage());
        }

        // Test3: out of stock
        System.out.println("\n3. Testing out of stock:");
        Cart outOfStockCart = new Cart();
        Customer customer3 = new Customer(outOfStockCart, 1000.0);
        Cheese limitedCheese = new Cheese("Limited Cheese", 100.0, 2, LocalDate.now().plusDays(7), 0.2);
        outOfStockCart.add(limitedCheese, 5); //more than available(2)
        try {
            ecommerceSystem.checkout(customer3, outOfStockCart);
        } catch (CheckoutException e) {
            System.out.println("☑ " + e.getMessage());
        }

        // Test4: expired product
        System.out.println("\n4. Testing expired product:");
        Cheese expiredCheese = new Cheese("Expired Cheese", 100.0, 5, LocalDate.now().minusDays(1), 0.2);
        Cart expiredCart = new Cart();
        Customer customer4 = new Customer(expiredCart, 1000.0);
        expiredCart.add(expiredCheese, 1);
        try {
            ecommerceSystem.checkout(customer4, expiredCart);
        } catch (CheckoutException e) {
            System.out.println("☑ " + e.getMessage());
        }
        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }
}