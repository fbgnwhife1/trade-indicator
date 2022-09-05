package tradeindicatorservice.tradeindicator.Repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tradeindicatorservice.tradeindicator.Entity.Market;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, String> {
    List<Market> findByMarketContaining(String code);
}
