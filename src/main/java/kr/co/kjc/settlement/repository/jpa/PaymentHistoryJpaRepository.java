package kr.co.kjc.settlement.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.jpa.PaymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

public interface PaymentHistoryJpaRepository extends JpaRepository<PaymentHistory, Long>,
    QueryByExampleExecutor<PaymentHistory>, CustomPaymentHistoryJpaRepository {

}

interface CustomPaymentHistoryJpaRepository {

}

@Repository
@RequiredArgsConstructor
class CustomPaymentHistoryJpaRepositoryImpl implements CustomPaymentHistoryJpaRepository {

  private final JPAQueryFactory queryFactory;

}
