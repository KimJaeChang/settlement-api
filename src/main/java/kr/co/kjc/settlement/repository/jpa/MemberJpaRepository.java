package kr.co.kjc.settlement.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.jpa.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface MemberJpaRepository extends JpaRepository<Member, Long>,
    JpaSpecificationExecutor<Member>, CustomMemberJpaRepository {

}

interface CustomMemberJpaRepository {

}

@Repository
@RequiredArgsConstructor
class CustomCustomMemberJpaRepositoryImpl implements CustomMemberJpaRepository {

  private final JPAQueryFactory queryFactory;

}
