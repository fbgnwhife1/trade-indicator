package tradeindicatorservice.tradeindicator.Repository.JPA;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tradeindicatorservice.tradeindicator.Entity.ClosePriceEntity;
import tradeindicatorservice.tradeindicator.Entity.MariaConclusion;
import tradeindicatorservice.tradeindicator.Indicator.QVolumeDataDto;
import tradeindicatorservice.tradeindicator.Indicator.VolumeDataDto;

import java.util.Date;
import java.util.List;

import static tradeindicatorservice.tradeindicator.Entity.QMariaConclusion.*;

@RequiredArgsConstructor
@Repository
public class QConclusionRepositoryImpl implements QConclusionRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<VolumeDataDto> findVolumeEachDate(String market, Date date) {
        return queryFactory
                .select(new QVolumeDataDto(mariaConclusion.code, mariaConclusion.trade_volume.sum(), mariaConclusion.date))
                .from(mariaConclusion)
                .where(mariaConclusion.code.eq(market)
                        .and(mariaConclusion.date.before(date))
                )
                .groupBy(mariaConclusion.date.dayOfYear())
                .orderBy(mariaConclusion.date.asc())
                .fetch();
    }

    @Override
    public MariaConclusion findClosePrice(String market, Date date) {
        return queryFactory
                .select(mariaConclusion)
                .from(mariaConclusion)
                .where(mariaConclusion.code.eq(market)
                        .and(mariaConclusion.date.before(date))
                )
                .orderBy(mariaConclusion.date.desc())
                .limit(1L)
                .fetchOne();
    }

    @Override
    public List<MariaConclusion> initClosePrice(String market, Date date) {
        return queryFactory
                .select(mariaConclusion)
                .from(mariaConclusion)
                .where(mariaConclusion.code.eq(market)
                        .and(mariaConclusion.date.before(date))
                )
                .groupBy(mariaConclusion.date.dayOfYear())
                .orderBy(mariaConclusion.date.desc())
                .fetch();
    }

    @Override
    public void deletePrevData(Date date) {
        queryFactory
                .delete(mariaConclusion)
                .where(mariaConclusion.date.before(date))
                .execute();
    }

    @Override
    public List<MariaConclusion> findBeforeDate(Date date) {
        return queryFactory
                .selectFrom(mariaConclusion)
                .where(mariaConclusion.date.before(date))
                .fetch();
    }


}
