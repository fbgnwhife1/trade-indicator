package tradeindicatorservice.tradeindicator.Repository.JPA;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import static tradeindicatorservice.tradeindicator.Entity.QClosePriceEntity.*;

@Repository
@RequiredArgsConstructor
public class AnalyzeRepositoryImpl implements AnalyzeRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BigDecimal> findRSI(String market, Integer period) {
        return queryFactory
                .select(closePriceEntity.lastPrice)
                .from(closePriceEntity)
                .where(closePriceEntity.market.eq(market))
                .groupBy(closePriceEntity.date.dayOfYear())
                .limit(period)
                .orderBy(closePriceEntity.date.desc())
                .fetch();
    }
}
