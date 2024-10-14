package kr.co.kjc.settlement.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "COMPANY")
public class Company extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "company_uuid")
  private String companyUuid;

  public static Company of(String companyName, String companyUuid) {
    Company company = new Company();
    company.companyName = companyName;
    company.companyUuid = companyUuid;
    return company;
  }
}
