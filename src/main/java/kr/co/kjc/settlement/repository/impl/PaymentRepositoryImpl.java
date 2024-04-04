package kr.co.kjc.settlement.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl {

  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;
}
