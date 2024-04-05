package kr.co.kjc.settlement.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.PaymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

}

interface CustomPaymentHistoryRepository {

}

@Repository
@RequiredArgsConstructor
class CustomPaymentHistoryRepositoryImpl implements CustomPaymentHistoryRepository {

  private final JPAQueryFactory queryFactory;

}
