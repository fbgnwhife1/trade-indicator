package tradeindicatorservice.tradeindicator.Entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@RedisHash(value = "DailyConclusionVolume")
public class RedisDailyConclusionVolume implements Serializable {
    private static final long serialVersionUID = -7353484588260422449L;

    @Id
    private Long id;

    private String code; // ex) KRW-BTC
    private BigDecimal trade_volume; // 체결량
    private Date date;  // 체결 시각

    public RedisDailyConclusionVolume(String code, BigDecimal trade_volume, Date date) {
        this.code = code;
        this.trade_volume = trade_volume;
        this.date = date;
    }
}
