package tradeindicatorservice.tradeindicator.Indicator;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import tradeindicatorservice.tradeindicator.Entity.RedisDailyConclusionVolume;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VolumeDataDto {

    private String market;
    private BigDecimal sum;
    private Date date;

    @QueryProjection
    public VolumeDataDto(String market, BigDecimal sum, Date date) {
        this.market = market;
        this.sum = sum;
        this.date = date;
    }

    public RedisDailyConclusionVolume transForRedis(){
        return new RedisDailyConclusionVolume(market, sum, date);
    }
}
