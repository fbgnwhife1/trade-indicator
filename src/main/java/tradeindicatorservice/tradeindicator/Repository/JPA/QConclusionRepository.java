package tradeindicatorservice.tradeindicator.Repository.JPA;

import tradeindicatorservice.tradeindicator.Entity.ClosePriceEntity;
import tradeindicatorservice.tradeindicator.Entity.MariaConclusion;
import tradeindicatorservice.tradeindicator.Indicator.VolumeDataDto;

import java.util.Date;
import java.util.List;

public interface QConclusionRepository {

    List<VolumeDataDto> findVolumeEachDate(String market, Date date);
    MariaConclusion findClosePrice(String market, Date date);
    List<MariaConclusion> initClosePrice(String market, Date date);
    void deletePrevData(Date date);
    List<MariaConclusion> findBeforeDate(Date date);
}
