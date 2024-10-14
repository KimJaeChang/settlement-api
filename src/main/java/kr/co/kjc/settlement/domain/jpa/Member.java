package kr.co.kjc.settlement.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;

@Entity
@Getter
@Table(name = "MEMBER")
public class Member extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
//  @SequenceGenerator(name = "PAYMENT_SEQ", schema = "CHASYGO", sequenceName = "PAYMENT_SEQ", allocationSize = 1, initialValue = 100)
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_SEQ")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "COMPANY_ID")
  private Company company;

  @Column(name = "member_userid")
  private String userId;

  @Column(name = "member_uuid")
  private String uuid;

  @Column(name = "member_username")
  private String userName;

  @Column(name = "member_handphone")
  private String handPhone;

  public static Member of(String userId, String userName, String handPhone, Company company) {
    Member result = new Member();
    result.userId = userId;
    result.uuid = UUID.randomUUID().toString();
    result.userName = userName;
    result.handPhone = handPhone;
    result.company = company;
    return result;
  }

  public static Member createExampleByUuid(String uuid) {
    Member result = new Member();
    result.uuid = uuid;
    return result;
  }
}
