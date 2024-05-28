package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenReqDTO;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenResDTO;

public interface JwtTokenService {

  JwtTokenResDTO create(JwtTokenReqDTO dto);

  JwtTokenResDTO update(JwtTokenReqDTO dto, MemberDTO memberDTO);

  boolean isAccessTokenExpired(String accessToken);

  boolean isRefreshTokenExpired(String refreshToken);

  MemberDTO findMemberByAccessToken(String accessToken);

  MemberDTO findMemberByRefreshToken(String refreshToken);
}
