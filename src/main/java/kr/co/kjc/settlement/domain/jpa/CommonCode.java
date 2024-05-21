package kr.co.kjc.settlement.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "COMMON_CODE")
public class CommonCode extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "parent_code")
  private String parentCode;

  @Column(name = "child_code")
  private String childCode;

  public static CommonCode of(String parentCode, String childCode) {
    CommonCode result = new CommonCode();
    result.parentCode = parentCode;
    result.childCode = childCode;
    return result;
  }

}
