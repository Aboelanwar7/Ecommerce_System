# E-commerce System

A Java-based e-commerce system that demonstrates object-oriented programming principles including inheritance, interfaces, and polymorphism. The system handles product management, shopping cart functionality, checkout processing, and shipping calculations.

## Features

- **Product Management**: Support for different product types (physical and digital)
- **Shopping Cart**: Add/remove products with quantity management
- **Inventory Management**: Real-time stock tracking and validation
- **Expiration Handling**: Automatic validation of product expiry dates
- **Shipping System**: Weight-based shipping calculations for physical products
- **Checkout Process**: Complete order processing with balance validation
- **Error Handling**: Comprehensive validation and exception handling

## Architecture

### Core Components

#### Models
- **Product Interface**: Base contract for all products
- **Product Implementations**:
  - `Cheese`: Perishable product with expiry dates (implements `Product`, `Expirable`, `Shippable`)
  - `TV`: Electronic product with shipping requirements (implements `Product`, `Shippable`)
  - `ScratchCard`: Digital product with no shipping (implements `Product`)
- **Cart**: Shopping cart management with product-quantity mapping
- **Customer**: Customer information with balance management

#### Services
- **EcommerceSystem**: Main checkout orchestrator
- **ShippingService**: Handles shipping calculations and logistics
- **Exception Handling**: Custom checkout exceptions

### Design Patterns

- **Interface Segregation**: Separate interfaces for `Product`, `Shippable`, and `Expirable`
- **Strategy Pattern**: Pluggable shipping service implementation
- **Decorator Pattern**: Products can implement multiple interfaces as needed

## Project Structure

```
src/
├── model/
│   ├── cart/
│   │   ├── Cart.java
│   │   └── CartShippableProd.java
│   ├── checkout/
│   │   └── CheckoutResult.java
│   ├── customer/
│   │   └── Customer.java
│   └── product/
│       ├── Product.java (interface)
│       ├── Expirable.java (interface)
│       ├── Shippable.java (interface)
│       └── impl/
│           ├── Cheese.java
│           ├── TV.java
│           └── ScratchCard.java
├── service/
│   ├── checkout/
│   │   ├── EcommerceSystem.java
│   │   └── CheckoutException.java
│   └── shipping/
│       ├── ShippingService.java (interface)
│       ├── ShippingServiceImp.java
│       ├── ShippableProd.java (interface)
│       └── ShippingResult.java
└── Main.java
```

## Dependencies

- **Lombok**: For reducing boilerplate code with annotations
  - `@AllArgsConstructor`: Constructor generation
  - `@Getter`: Getter method generation
- **Java 8+**: For Stream API and LocalDate functionality

## Getting Started

### Prerequisites
- Java 8 or higher
- Lombok plugin for your IDE
- Maven or Gradle (for dependency management)

### Installation

1. Clone the repository
2. Ensure Lombok is properly configured in your IDE
3. Compile and run the `Main.java` class

### Running the Demo

```bash
javac -cp ".:lombok.jar" Main.java
java -cp ".:lombok.jar" Main
```

## Sample Test Case

<img width="534" height="869" alt="Screenshot 2025-07-12 005836" src="https://github.com/user-attachments/assets/c9541b11-89ec-4358-a9a1-da3b8112b4cb" />
<img width="638" height="498" alt="Screenshot 2025-07-12 005854" src="https://github.com/user-attachments/assets/079f3be2-7359-42ae-8032-c0d4779fb641" />


## Key Features Explained

### Product Types
- **Shippable Products**: Have weight and incur shipping costs
- **Expirable Products**: Have expiry dates and cannot be sold when expired
- **Digital Products**: No shipping required, no expiry

### Shipping Calculation
- Rate: $30 per kilogram
- Only applies to products implementing `Shippable` interface
- Automatically calculates total weight and shipping fee

### Validation Rules
- Products must be in stock
- Expirable products cannot be expired
- Customer must have sufficient balance
- Cart cannot be empty during checkout

### Error Handling
The system handles various error scenarios:
- Empty cart checkout
- Insufficient customer balance
- Out of stock products
- Expired products
- Invalid quantities

## Testing

The `Main.java` class includes comprehensive test cases:
- Successful checkout scenario
- Empty cart validation
- Insufficient balance handling
- Out of stock detection
- Expired product validation
