package kr.co.kjc.settlement.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@Table(name = "COMMON_CODE")
@ToString
public class CommonCode extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "parent_code")
  private String parentCode;

  @Column(name = "child_code")
  private String childCode;

  @Column(name = "code_name")
  private String codeName;

  @Column(name = "description")
  private String description;

  public static CommonCode of(String parentCode, String childCode) {
    CommonCode result = new CommonCode();
    result.parentCode = parentCode;
    result.childCode = childCode;
    return result;
  }

  public static CommonCode createExampleByParentCode(String parentCode) {
    CommonCode result = new CommonCode();
    result.parentCode = parentCode;
    return result;
  }

  public static CommonCode createExampleByChildCode(String childCode) {
    CommonCode result = new CommonCode();
    result.childCode = childCode;
    return result;
  }
}
