package kr.co.kjc.settlement.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.jpa.CommonCode;
import kr.co.kjc.settlement.domain.jpa.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface CommonCodeJpaRepository extends JpaRepository<CommonCode, Long>,
    JpaSpecificationExecutor<Member>, CustomCommonCodeJpaRepository {

}

interface CustomCommonCodeJpaRepository {

}

@Repository
@RequiredArgsConstructor
class CustomCustomCommonCodeJpaRepositoryImpl implements CustomCommonCodeJpaRepository {

  private final JPAQueryFactory queryFactory;

}
