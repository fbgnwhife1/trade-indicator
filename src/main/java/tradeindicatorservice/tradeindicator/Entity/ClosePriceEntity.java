package tradeindicatorservice.tradeindicator.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "close_price")
@Data
@NoArgsConstructor
public class ClosePriceEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String market;
    private BigDecimal lastPrice;
    private LocalDateTime date;

    public ClosePriceEntity(String market, BigDecimal lastPrice, LocalDateTime date) {
        this.market = market;
        this.lastPrice = lastPrice;
        this.date = date;
    }


}
