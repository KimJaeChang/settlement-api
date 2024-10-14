package kr.co.kjc.settlement.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.jpa.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long>,
    QueryByExampleExecutor<Payment>, CustomPaymentJpaRepository {

}

interface CustomPaymentJpaRepository {

}

@Repository
@RequiredArgsConstructor
class CustomPaymentJpaRepositoryImpl implements CustomPaymentJpaRepository {

  private final JPAQueryFactory queryFactory;

}
