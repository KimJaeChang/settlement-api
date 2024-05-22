package kr.co.kjc.settlement.global.config.local;

import jakarta.annotation.PostConstruct;
import java.util.Optional;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import kr.co.kjc.settlement.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
@EnableJpaAuditing
@RequiredArgsConstructor
public class LocalJpaAuditAwareProvider implements AuditorAware<String> {

  private static ThreadLocal<String> CURRENT_TOKEN = new ThreadLocal<>();

  private final JwtTokenService jwtTokenService;

  @PostConstruct
  void init() {
    CURRENT_TOKEN.set(CommonConstants.ADMIN_UUID);
  }

  @Override
  public Optional<String> getCurrentAuditor() {
    String adminUuid = CURRENT_TOKEN.get();
    return Optional.ofNullable(adminUuid);
  }
}
