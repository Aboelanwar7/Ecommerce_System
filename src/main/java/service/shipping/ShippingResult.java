package service.shipping;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShippingResult {
    private double shippingFee;
    private String shipmentDetails;
}
