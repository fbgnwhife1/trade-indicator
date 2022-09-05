package tradeindicatorservice.tradeindicator.Repository.JPA;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tradeindicatorservice.tradeindicator.Entity.MariaConclusion;
import tradeindicatorservice.tradeindicator.Entity.RedisDailyConclusionVolume;

import java.util.Date;
import java.util.List;

@Repository
public interface ConclusionRepository extends JpaRepository<MariaConclusion, String> {

}
