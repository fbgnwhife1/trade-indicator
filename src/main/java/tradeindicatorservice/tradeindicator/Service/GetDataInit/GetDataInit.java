package tradeindicatorservice.tradeindicator.Service.GetDataInit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.Mark;
import tradeindicatorservice.tradeindicator.Entity.MariaConclusion;
import tradeindicatorservice.tradeindicator.Entity.Market;
import tradeindicatorservice.tradeindicator.Repository.JPA.MarketRepository;
import tradeindicatorservice.tradeindicator.Service.BSISocket.RunSocket;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetDataInit {
    private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
        initService.openSocket();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final RunSocket runSocket;
        private final MarketRepository marketRepository;

        public void openSocket() throws InterruptedException {
            List<Market> all = marketRepository.findAll();

            List<String> marketList = new ArrayList<>();

            for (Market market : all) {
                marketList.add(market.getMarket());

                if(marketList.size() == 15){
                    runSocket.runSocket(marketList);
                    marketList.clear();
                    Thread.sleep(1000);
                }
            }
            if(!marketList.isEmpty()){
                runSocket.runSocket(marketList);
            }
        }
    }
}
