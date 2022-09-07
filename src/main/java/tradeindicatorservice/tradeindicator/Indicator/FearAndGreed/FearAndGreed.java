package tradeindicatorservice.tradeindicator.Indicator.FearAndGreed;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@RedisHash(value = "FearAndGreed")
public class FearAndGreed implements Serializable {
    private static final long serialVersionUID = -987655413131L;

    private String name;
    private List<FnG> data;
    private FnGMetaData metaData;

    @Data
    private static class FnG {
        Integer value;
        String value_classification;
        Long timestamp;
        Integer time_until_update;
    }

    @Data
    private static class FnGMetaData {
        String error;
    }
}
