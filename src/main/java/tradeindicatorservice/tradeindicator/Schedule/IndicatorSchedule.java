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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class IndicatorSchedule {

    private final AnalyzeRepository analyzeRepository;
    private final MarketRepository marketRepository;
    private final RedisTemplate<String, Double> redisTemplate;
    private final AnalyzeService analyzeService;


}
