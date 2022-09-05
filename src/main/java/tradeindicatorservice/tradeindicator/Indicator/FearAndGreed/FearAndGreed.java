package tradeindicatorservice.tradeindicator.Indicator.FearAndGreed;

import lombok.Data;

import java.util.List;

@Data
public class FearAndGreed {

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
