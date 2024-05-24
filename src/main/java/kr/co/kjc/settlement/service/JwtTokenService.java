package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.domain.redis.Token;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenRefreshReqDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenReqDTO;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenResDTO;

public interface JwtTokenService {

  JwtTokenResDTO create(JwtTokenReqDTO dto);

  Token update(JwtTokenRefreshReqDTO dto);

  boolean isAccessTokenExpired(String accessToken);

  boolean isRefreshTokenExpired(String refreshToken);

  MemberDTO findMemberByToken(String accessToken);
}
