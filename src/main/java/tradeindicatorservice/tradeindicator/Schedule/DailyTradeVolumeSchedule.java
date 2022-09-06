package tradeindicatorservice.tradeindicator.Schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tradeindicatorservice.tradeindicator.Entity.MariaConclusion;
import tradeindicatorservice.tradeindicator.Entity.Market;
import tradeindicatorservice.tradeindicator.Indicator.VolumeDataDto;
import tradeindicatorservice.tradeindicator.Repository.JPA.ClosePriceRepository;
import tradeindicatorservice.tradeindicator.Repository.JPA.ConclusionRepository;
import tradeindicatorservice.tradeindicator.Repository.JPA.MarketRepository;
import tradeindicatorservice.tradeindicator.Repository.JPA.QConclusionRepository;
import tradeindicatorservice.tradeindicator.Repository.Redis.RedisConclusionRepository;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyTradeVolumeSchedule {

    private final MarketRepository marketRepository;
    private final ClosePriceRepository closePriceRepository;
    private final RedisConclusionRepository redisConclusionRepository;
    private final QConclusionRepository qConclusionRepository;
    private final ConclusionRepository conclusionRepository;
    private final CSVMapper csvMapper = new CSVMapper();

    @Scheduled(cron = "0 0 0 * * *")
    public void dailyVolumeSchedule(){
        List<Market> all = marketRepository.findAll();
        Date date  = java.sql.Timestamp.valueOf(LocalDateTime.now());

        for (Market market : all) {
            List<VolumeDataDto> volumeEachDate = qConclusionRepository.findVolumeEachDate(market.getMarket(), date);
            for (VolumeDataDto data : volumeEachDate) {
                redisConclusionRepository.save(data.transForRedis());
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void dailyClosePriceSchedule(){
        List<Market> all = marketRepository.findAll();

        Date date  = java.sql.Timestamp.valueOf(LocalDateTime.now());

        for (Market market : all) {
            MariaConclusion close = qConclusionRepository.findClosePrice(market.getMarket(), date);
            closePriceRepository.save(close.transCloseData());
        }
    }

    @Scheduled(cron = "2 0 0 * * *")
    public void dailyRemoveSchedule(){
        Date prevDate  = java.sql.Timestamp.valueOf(LocalDateTime.now().minusHours(2));
        List<MariaConclusion> list = qConclusionRepository.findBeforeDate(prevDate);
        LocalDateTime now = LocalDateTime.now();

        File csvFile = new File("C:\\Users\\Shin\\IdeaProjects\\trade-indicator\\src\\main\\resources\\csv/" + now.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))+now.getHour()+now.getMinute() + ".csv");

        csvMapper.csvFileOut(MariaConclusion.class, csvFile, list);
        conclusionRepository.deleteAll(list);
    }
}
