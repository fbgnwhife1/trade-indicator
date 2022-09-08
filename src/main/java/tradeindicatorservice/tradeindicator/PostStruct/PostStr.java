package tradeindicatorservice.tradeindicator.PostStruct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tradeindicatorservice.tradeindicator.Entity.MariaConclusion;
import tradeindicatorservice.tradeindicator.Entity.Market;
import tradeindicatorservice.tradeindicator.Repository.JPA.ClosePriceRepository;
import tradeindicatorservice.tradeindicator.Repository.JPA.MarketRepository;
import tradeindicatorservice.tradeindicator.Repository.JPA.QConclusionRepository;
import tradeindicatorservice.tradeindicator.Service.AnalyzeService;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostStr {

    private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
//        initService.firstRsi();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final QConclusionRepository qConclusionRepository;
        private final MarketRepository marketRepository;
        private final ClosePriceRepository closePriceRepository;
        private final AnalyzeService analyzeService;
//        public void initClosePrice() {
//            List<Market> all = marketRepository.findAll();
//            for (Market market : all) {
//                List<MariaConclusion> closePrice = qConclusionRepository.initClosePrice(market.getMarket(), new Date());
//                for (MariaConclusion mariaConclusion : closePrice) {
//                    closePriceRepository.save(mariaConclusion.transCloseData());
//                }
//            }
//        }

//        public void firstRsi(){
//            List<Market> all = marketRepository.findAll();
//            for (Market market : all) {
//                String key = AnalyzeService.KeyGen.cartKeyGenerate(market.getMarket());
//                analyzeService.subRSI(market.getMarket(), key);
//            }
//        }

    }
}
