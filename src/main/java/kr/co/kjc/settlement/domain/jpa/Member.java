package kr.co.kjc.settlement.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

  @Column(name = "userid")
  private String userId;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "username")
  private String userName;

  @Column(name = "handphone")
  private String handPhone;

  public static Member of(Long id, String userId, String userName, String handPhone) {
    Member result = new Member();
    result.id = id;
    result.userId = userId;
    result.uuid = UUID.randomUUID().toString();
    result.userName = userName;
    result.handPhone = handPhone;
    return result;
  }
}
