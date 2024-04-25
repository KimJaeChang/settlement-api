package kr.co.kjc.settlement.global.config.local;

import jakarta.annotation.PostConstruct;
import java.util.List;
import kr.co.kjc.settlement.domain.jpa.Member;
import kr.co.kjc.settlement.repository.jpa.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class TestDataConfig {

  private final MemberJpaRepository memberJpaRepository;

  @PostConstruct
  void init() {
    createMembers();  // 테스트 유저 생성
  }

  @Transactional
  void createMembers() {
    Member testUser1 = Member.of("test-user1", "테스트유저1", "01011112222");
    List<Member> testMemebers = List.of(testUser1);

    testMemebers.stream()
        .forEach(e -> {
          memberJpaRepository.save(e);
        });
  }


}
