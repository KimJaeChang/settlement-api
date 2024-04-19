package kr.co.kjc.settlement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "PAYMENT_HISTORY")
public class PaymentHistory extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
//  @SequenceGenerator(name = "PAYMENT_SEQ", schema = "CHASYGO", sequenceName = "PAYMENT_SEQ", allocationSize = 1, initialValue = 100)
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_SEQ")
  private Long id;

  @Column(name = "payment_id")
  private Long paymentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", referencedColumnName = "id")
  private Member member;

}
