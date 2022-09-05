package tradeindicatorservice.tradeindicator.Indicator;

import lombok.Data;

@Data
public class OrderBookResult {
    private String market; // market

    private Double ask_price;
    private Double bid_price;
    private Double ask_size;
    private Double bid_size;

}
