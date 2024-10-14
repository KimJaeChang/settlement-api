package kr.co.kjc.settlement.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.jpa.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

public interface CompanyJpaRepository extends JpaRepository<Company, Long>,
    QueryByExampleExecutor<Company>, CustomCompanyJpaRepository {

}

interface CustomCompanyJpaRepository {

}

@Repository
@RequiredArgsConstructor
class CustomCompanyJpaRepositoryImpl implements CustomCompanyJpaRepository {

  private final JPAQueryFactory queryFactory;

}
