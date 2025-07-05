package service.checkout;

import model.cart.Cart;
import model.checkout.CheckoutResult;
import model.customer.Customer;
import model.product.Expirable;
import model.product.Product;
import service.shipping.ShippableProd;
import service.shipping.ShippingService;
import service.shipping.ShippingServiceImp;

import java.util.List;
import java.util.Map;

public class EcommerceSystem {
    private ShippingService shippingService;

    public EcommerceSystem() {
        this.shippingService = new ShippingServiceImp();
    }

    public EcommerceSystem(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public CheckoutResult checkout(Customer customer, Cart cart) throws CheckoutException {
        validateCheckout(customer, cart);
        double subtotal = cart.getSubtotal();
        List<ShippableProd> shippableItems = cart.getShippableItems();
        double shippingFee = shippingService.calculateShippingFee(shippableItems);
        double total = subtotal + shippingFee;
        if (customer.getBalance() < total) {
            throw new CheckoutException("Insufficient balance. Required: $" + total +
                    ", Available: $" + customer.getBalance());
        }
        if (!shippableItems.isEmpty()) {
            shippingService.shipProducts(shippableItems);
        }
        printCheckoutReceipt(cart.getProducts(), subtotal, shippingFee, total);
        customer.deductBalance(total);
        double balanceAfter = customer.getBalance();
        cart.processCheckout();
        return new CheckoutResult(subtotal, shippingFee, total, balanceAfter);
    }

    private void validateCheckout(Customer customer, Cart cart) throws CheckoutException {
        if (cart.isEmpty()) {
            throw new CheckoutException("Cart is empty");
        }
        for (Map.Entry<Product, Integer> item : cart.getProducts().entrySet()) {
            Product product = item.getKey();
            int requestedQuantity = item.getValue();
            if (!product.isAvailable(requestedQuantity)) {
                if (product instanceof Expirable && ((Expirable) product).isExpired()) {
                    throw new CheckoutException("Product expired: " + product.getName());
                } else {
                    throw new CheckoutException("Product out of stock: " + product.getName() +
                            " (requested: " + requestedQuantity +
                            ", available: " + product.getQuantity() + ")");
                }
            }
        }
    }

    private void printCheckoutReceipt(Map<Product, Integer> products, double subtotal,
                                      double shippingFee, double total) {
        System.out.println("\n** Checkout receipt **");

        for (Map.Entry<Product, Integer> item : products.entrySet()) {
            Product product = item.getKey();
            int quantity = item.getValue();
            double itemTotal = product.getPrice() * quantity;

            System.out.printf("%dx %s %.0f%n", quantity, product.getName(), itemTotal);
        }

        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingFee);
        System.out.printf("Amount %.0f%n", total);
    }
}