package tradeindicatorservice.tradeindicator.Service.BSISocket;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderBookResult {
    private String type; // trade
    private String code; // KRW-BTC

    private Double total_ask_size;
    private Double total_bid_size;
    List<OrderBookUnit> orderbook_units;
    private Long timestamp;

    private static class OrderBookUnit {
        private Double ask_price;
        private Double bid_price;
        private Double ask_size;
        private Double bid_size;
    }


    public BSI_RBIDto toResult() {
        BSI_RBIDto dto = new BSI_RBIDto();
        dto.setMarket(this.code);
        double score = 0.0;
        for (OrderBookUnit unit : orderbook_units) {
            score += (unit.bid_size)/(unit.bid_size + unit.ask_size);
        }

        score = score/orderbook_units.size();
        dto.setScore(score);
        dto.setGo(score >= 0.5);
        return dto;
    }
}