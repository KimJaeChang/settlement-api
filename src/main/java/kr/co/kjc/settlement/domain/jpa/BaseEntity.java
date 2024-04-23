package kr.co.kjc.settlement.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

  @CreatedDate
  @Column(name = "CREATED_AT", updatable = false)
  protected LocalDateTime createdAt;

  @CreatedBy
  @Column(name = "CREATED_BY", updatable = false)
  protected String createdBy;

  @LastModifiedDate
  @Column(name = "UPDATED_AT")
  protected LocalDateTime updatedAt;

  @LastModifiedBy
  @Column(name = "UPDATED_BY")
  protected String updatedBy;

}
