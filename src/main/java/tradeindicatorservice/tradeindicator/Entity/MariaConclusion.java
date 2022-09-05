package tradeindicatorservice.tradeindicator.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity(name = "Conclusion")
@Data
@NoArgsConstructor
public class MariaConclusion {

    @Id
    private Long id;
    private String code; // ex) KRW-BTC
    private Long trade_timestamp; // 체결 타임스탬프 (millisecond)
    private BigDecimal trade_price; // 체결 가격
    private BigDecimal trade_volume; // 체결량
    private BigDecimal real_price;
    public String ask_bid; // ASK 매수/ BID매도 구분
    private Date date;  // 체결 시각

    public ClosePriceEntity transCloseData(){
        LocalDateTime localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

//        String format = localDate.format(DateTimeFormatter.ISO_DATE);
//        localDate = LocalDateTime.parse(format);
        return new ClosePriceEntity(code, trade_price, localDate);
    }
}
