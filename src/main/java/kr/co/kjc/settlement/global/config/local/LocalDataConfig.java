package kr.co.kjc.settlement.global.config.local;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import kr.co.kjc.settlement.domain.jpa.CommonCode;
import kr.co.kjc.settlement.domain.jpa.Company;
import kr.co.kjc.settlement.domain.jpa.Member;
import kr.co.kjc.settlement.repository.jpa.CommonCodeJpaRepository;
import kr.co.kjc.settlement.repository.jpa.CompanyJpaRepository;
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
  private final CompanyJpaRepository companyJpaRepository;

  @PostConstruct
  void init() {
    if (!ddlAuto.equals("none")) {
      createCommonCodes(); // 테스트 관리용 CommonCode 생성
      createCompanysAndMembers(); // 테스트 법인 & 유저 생성
    }
  }

  @Transactional
  void createCommonCodes() {
    CommonCode testData1 = CommonCode.of("AAA000", "AAA001");
    CommonCode testData2 = CommonCode.of("AAA000", "AAA002");
    CommonCode testData3 = CommonCode.of("BBB000", "BBB001");
    CommonCode testData4 = CommonCode.of("BBB000", "BBB002");
    CommonCode testData5 = CommonCode.of("CCC000", "CCC001");
    CommonCode testData6 = CommonCode.of("CCC000", "CCC002");

    List<CommonCode> testDatas = List.of(testData1, testData2, testData3, testData4,
        testData5, testData6);
    commonCodeJpaRepository.saveAll(testDatas);
  }

//  @Transactional
//  void createMembers() {
//    Member testData1 = Member.of("test-user1", "테스트유저1", "01011111111");
//    Member testData2 = Member.of("test-user2", "테스트유저2", "01022222222");
//    Member testData3 = Member.of("test-user3", "테스트유저3", "01033333333");
//
//    List<Member> testDatas = List.of(testData1, testData2, testData3);
//    memberJpaRepository.saveAll(testDatas);
//  }

  @Transactional
  void createCompanysAndMembers() {
    Company testData1 = Company.of("test-company1", UUID.randomUUID().toString());
    Company saveCompany1 = companyJpaRepository.save(testData1);
    Member testMember1 = Member.of("test-user1", "테스트유저1", "01011111111", saveCompany1);
    Member testMember2 = Member.of("test-user2", "테스트유저2", "01022222222", saveCompany1);
    memberJpaRepository.save(testMember1);
    memberJpaRepository.save(testMember2);

    Company testData2 = Company.of("test-company2", UUID.randomUUID().toString());
    Company saveCompany2 = companyJpaRepository.save(testData2);
    Member testMember3 = Member.of("test-user3", "테스트유저3", "01033333333", saveCompany2);
    memberJpaRepository.save(testMember3);
  }
}
