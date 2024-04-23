package kr.co.kjc.settlement.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface PaymentRepository extends JpaRepository<Payment, Long>,
    JpaSpecificationExecutor<Payment>, CustomPaymentRepository {

}

interface CustomPaymentRepository {

}

@Repository
@RequiredArgsConstructor
class CustomPaymentRepositoryImpl implements CustomPaymentRepository {

  private final JPAQueryFactory queryFactory;

}
