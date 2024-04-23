package kr.co.kjc.settlement.global.config.database;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EntityScan(basePackages = {"kr.co.kjc.settlement.domain.jpa"})
//@EnableJpaRepositories(basePackages = {"kr.co.kjc.settlement.repository.jpa"})
public class QuerydslConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(this.entityManager);
  }
}
