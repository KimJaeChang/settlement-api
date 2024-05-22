package kr.co.kjc.settlement.global.config.local;

import jakarta.annotation.PostConstruct;
import java.util.List;
import kr.co.kjc.settlement.domain.jpa.CommonCode;
import kr.co.kjc.settlement.domain.jpa.Member;
import kr.co.kjc.settlement.repository.jpa.CommonCodeJpaRepository;
import kr.co.kjc.settlement.repository.jpa.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class LocalDataConfig {

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String ddlAuto;

  private final CommonCodeJpaRepository commonCodeJpaRepository;
  private final MemberJpaRepository memberJpaRepository;

  @PostConstruct
  void init() {
    if (!ddlAuto.equals("none")) {
      createCommonCodes(); // 테스트 관리용 CommonCode 생성
      createMembers();  // 테스트 유저 생성
    }
  }

  @Transactional
  void createCommonCodes() {
    CommonCode commonCode1 = CommonCode.of("AAA000", "AAA001");
    CommonCode commonCode2 = CommonCode.of("AAA000", "AAA002");
    CommonCode commonCode3 = CommonCode.of("BBB000", "BBB001");
    CommonCode commonCode4 = CommonCode.of("BBB000", "BBB002");
    CommonCode commonCode5 = CommonCode.of("CCC000", "CCC001");
    CommonCode commonCode6 = CommonCode.of("CCC000", "CCC002");
    List<CommonCode> testCommonCodes = List.of(commonCode1, commonCode2, commonCode3, commonCode4,
        commonCode5, commonCode6);
    testCommonCodes.stream()
        .forEach(e -> {
          commonCodeJpaRepository.save(e);
        });
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
