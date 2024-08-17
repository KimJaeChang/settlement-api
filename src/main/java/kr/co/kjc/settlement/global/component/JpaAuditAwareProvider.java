package kr.co.kjc.settlement.global.component;

import java.util.Optional;
import kr.co.kjc.settlement.global.dtos.member.MemberDTO;
import kr.co.kjc.settlement.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Profile("!local")
@Component
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaAuditAwareProvider implements AuditorAware<String> {

  private static ThreadLocal<String> CURRENT_TOKEN = new ThreadLocal<>();

  private final JwtTokenService jwtTokenService;

  public void create(String accessToken) {
    CURRENT_TOKEN.set(accessToken);
  }

  @Override
  public Optional<String> getCurrentAuditor() {

    String accessToken = CURRENT_TOKEN.get();

    if (!StringUtils.hasText(accessToken)) {
      MemberDTO memberDTO = jwtTokenService.findMemberByAccessToken(accessToken);
      return Optional.ofNullable(memberDTO.getUuid());
    }

    return Optional.empty();
  }
}
