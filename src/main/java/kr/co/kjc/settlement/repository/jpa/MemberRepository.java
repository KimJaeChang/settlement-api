package kr.co.kjc.settlement.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kjc.settlement.domain.jpa.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository<Member, Long>,
    JpaSpecificationExecutor<Member>, CustomMemberRepository {

}

interface CustomMemberRepository {

}

@Repository
@RequiredArgsConstructor
class CustomCustomMemberRepositoryImpl implements CustomMemberRepository {

  private final JPAQueryFactory queryFactory;

}
