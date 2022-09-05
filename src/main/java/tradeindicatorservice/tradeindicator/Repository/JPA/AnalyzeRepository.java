package tradeindicatorservice.tradeindicator.Repository.JPA;


import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AnalyzeRepository {
    List<BigDecimal> findRSI(String market, Integer period);
}
