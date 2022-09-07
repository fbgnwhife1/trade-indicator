package tradeindicatorservice.tradeindicator.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tradeindicatorservice.tradeindicator.ApiConnect.FearAndGreedApiConnect;
import tradeindicatorservice.tradeindicator.ApiConnect.OrderBookConnect;
import tradeindicatorservice.tradeindicator.Entity.Market;
import tradeindicatorservice.tradeindicator.Indicator.FearAndGreed.FearAndGreed;
import tradeindicatorservice.tradeindicator.Indicator.OrderBookResult;
import tradeindicatorservice.tradeindicator.Indicator.RSI.RSI;
import tradeindicatorservice.tradeindicator.Repository.JPA.AnalyzeRepository;
import tradeindicatorservice.tradeindicator.Repository.JPA.MarketRepository;
//import tradeindicatorservice.tradeindicator.Repository.JPA.AnalyzeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final RedisTemplate<String, Double> redisTemplate;
    private final OrderBookConnect orderBookService = new OrderBookConnect();
    private final FearAndGreedApiConnect fearAndGreedApiConnect = new FearAndGreedApiConnect();
    private final AnalyzeRepository analyzeRepository;
    private final RedisTemplate<String, FearAndGreed> FnGTemplate;

    public double[] BSI(String market) {
        List<OrderBookResult> orderBookList = orderBookService.getOrderBook(market);

        double[] bsi = new double[orderBookList.size()];

        for (int i = 0; i < bsi.length; i++) {
            bsi[i] = (double) orderBookList.get(i).getBid_size() / (orderBookList.get(i).getAsk_size() + orderBookList.get(i).getBid_size());
        }

        return bsi;
    }

    public FearAndGreed getFnG() {
        String key = "fear&greed";
        if(redisTemplate.opsForValue().get(key) != null){
            return FnGTemplate.opsForValue().get(key);
        }

        FearAndGreed result = fearAndGreedApiConnect.getFGIndex();
        FnGTemplate.opsForValue().set(key, result);
        FnGTemplate.expire(key, 10, TimeUnit.MINUTES);
        return result;
    }

    public double getRsi(String market){
        String key = AnalyzeService.KeyGen.cartKeyGenerate(market);
        Double cache = redisTemplate.opsForValue().get(key);
        if(cache != null){
            return cache;
        }

        return subRSI(market, key);
    }

    public double subRSI(String market, String key){
        RSI rsi = new RSI(14);
        List<BigDecimal> temp = analyzeRepository.findRSI(market, 14);
        List<BigDecimal> prizeList = new ArrayList<>();
        for (int i = temp.size()-1; i >= 0; i--) {
            prizeList.add(temp.get(i));
        }

        double[] prizes = new double[prizeList.size()];
        for (int i = 0; i < prizes.length; i++) {
            prizes[i] = prizeList.get(i).doubleValue();
        }

        double count = rsi.count(prizes);
        redisTemplate.opsForValue().set(key, count);
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
        return count;
    }

    public double no_cache_rsi(String market){
        RSI rsi = new RSI(14);
        List<BigDecimal> temp = analyzeRepository.findRSI(market, 14);
        List<BigDecimal> prizeList = new ArrayList<>();
        for (int i = temp.size()-1; i >= 0; i--) {
            prizeList.add(temp.get(i));
        }

        double[] prizes = new double[prizeList.size()];
        for (int i = 0; i < prizes.length; i++) {
            prizes[i] = prizeList.get(i).doubleValue();
        }

        return rsi.count(prizes);
    }


    public static class KeyGen {
        private static final String MARKET_KEY = "market";

        public static String cartKeyGenerate(String market){
            return MARKET_KEY + ":" + market;
        }
    }
}
