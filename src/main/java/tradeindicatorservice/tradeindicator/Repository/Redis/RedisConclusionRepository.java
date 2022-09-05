package tradeindicatorservice.tradeindicator.Repository.Redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tradeindicatorservice.tradeindicator.Entity.RedisDailyConclusionVolume;

@Repository
public interface RedisConclusionRepository extends CrudRepository<RedisDailyConclusionVolume, String> {
}
