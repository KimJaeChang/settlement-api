package kr.co.kjc.settlement.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.jpa.CommonCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

public interface CommonCodeJpaRepository extends JpaRepository<CommonCode, Long>,
    QueryByExampleExecutor<CommonCode>, CustomCommonCodeJpaRepository {

}

interface CustomCommonCodeJpaRepository {

}

@Repository
@RequiredArgsConstructor
class CustomCommonCodeJpaRepositoryImpl implements CustomCommonCodeJpaRepository {

  private final JPAQueryFactory queryFactory;

}
