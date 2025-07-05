package service.shipping;

import java.util.List;

public interface ShippingService {
    ShippingResult shipProducts(List<ShippableProd> products);
    double calculateShippingFee(List<ShippableProd> products);
}
