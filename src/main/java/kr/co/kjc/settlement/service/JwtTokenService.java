package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.jwt.JwtTokenReqDTO;
import kr.co.kjc.settlement.global.dtos.jwt.JwtTokenResDTO;
import kr.co.kjc.settlement.global.dtos.member.MemberDTO;

public interface JwtTokenService {

  JwtTokenResDTO create(JwtTokenReqDTO dto);

  JwtTokenResDTO update(String beforeRefreshToken, JwtTokenReqDTO dto, MemberDTO memberDTO);

  boolean isAccessTokenExpired(String accessToken);

  boolean isRefreshTokenExpired(String refreshToken);

  MemberDTO findMemberByAccessToken(String accessToken);

  MemberDTO findMemberByRefreshToken(String refreshToken);
}
