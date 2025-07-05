package service.shipping;

import java.util.List;

public class ShippingServiceImp implements ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 30.0; // $30 per kg

    @Override
    public ShippingResult shipProducts(List<ShippableProd> items) {
        if (items.isEmpty()) {
            return new ShippingResult(0.0, "No items to ship");
        }

        System.out.println("** Shipment notice **");

        double totalWeight = 0.0;
        StringBuilder shipmentDetails = new StringBuilder();

        for (ShippableProd item : items) {
            double weightInGrams = item.getWeight() * 1000;
            String itemDetails = String.format("%s %.0fg", item.getName(), weightInGrams);
            System.out.println(itemDetails);
            shipmentDetails.append(itemDetails).append("\n");
            totalWeight += item.getWeight();
        }

        System.out.printf("Total package weight %.1fkg%n", totalWeight);
        shipmentDetails.append(String.format("Total package weight %.1fkg", totalWeight));

        double shippingFee = calculateShippingFee(items);
        return new ShippingResult(shippingFee, shipmentDetails.toString());
    }

    @Override
    public double calculateShippingFee(List<ShippableProd> items) {
        if (items.isEmpty()) return 0.0;

        double totalWeight = items.stream()
                .mapToDouble(ShippableProd::getWeight)
                .sum();

        return totalWeight * SHIPPING_RATE_PER_KG;
    }
}