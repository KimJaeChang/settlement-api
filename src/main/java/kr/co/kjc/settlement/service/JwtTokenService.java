package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.response.JwtTokenDTO;

public interface JwtTokenService {

  JwtTokenDTO save(String uuid);

  JwtTokenDTO saveRefreshToken(String uuid);

  void update(String uuid);

}
