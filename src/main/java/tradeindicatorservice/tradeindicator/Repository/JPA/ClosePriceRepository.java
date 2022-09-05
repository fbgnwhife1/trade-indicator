package tradeindicatorservice.tradeindicator.Repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import tradeindicatorservice.tradeindicator.Entity.ClosePriceEntity;

public interface ClosePriceRepository extends JpaRepository<ClosePriceEntity, String> {
}
