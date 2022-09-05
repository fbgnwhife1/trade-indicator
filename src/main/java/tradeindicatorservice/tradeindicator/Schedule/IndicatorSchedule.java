package tradeindicatorservice.tradeindicator.Schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tradeindicatorservice.tradeindicator.Entity.Market;
import tradeindicatorservice.tradeindicator.Indicator.RSI.RSI;
import tradeindicatorservice.tradeindicator.Repository.JPA.AnalyzeRepository;
import tradeindicatorservice.tradeindicator.Repository.JPA.MarketRepository;
import tradeindicatorservice.tradeindicator.Service.AnalyzeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class IndicatorSchedule {

    private final AnalyzeRepository analyzeRepository;
    private final MarketRepository marketRepository;
    private final RedisTemplate<String, Double> redisTemplate;

    @Scheduled(cron = "* 10 * * * *")
    public void dailyRSISchedule() {
        List<Market> all = marketRepository.findAll();

        for (Market market : all) {
            String key = AnalyzeService.KeyGen.cartKeyGenerate(market.getMarket());

            RSI rsi = new RSI(14);
            List<BigDecimal> prizeList = analyzeRepository.findRSI(market.getMarket(), 14);

            double[] prizes = new double[prizeList.size()];
            for (int i = 0; i < prizes.length; i++) {
                prizes[i] = prizeList.get(i).doubleValue();
            }

            double count = rsi.count(prizes);
            redisTemplate.opsForValue().set(key, count);
            redisTemplate.expire(market.getMarket(), 30, TimeUnit.MINUTES);
        }
    }
}
