package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenRefreshReqDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenReqDTO;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenResDTO;

public interface JwtTokenService {

  JwtTokenResDTO create(JwtTokenReqDTO dto);

  void update(JwtTokenRefreshReqDTO dto);

  boolean isExpired(String accessToken);

  MemberDTO findMemberByToken(String token);
}
