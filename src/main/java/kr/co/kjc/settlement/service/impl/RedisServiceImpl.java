package kr.co.kjc.settlement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kjc.settlement.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper mapper;

  @Override
  public void save(String key, Object value) {
    redisTemplate.opsForValue()
        .set(key, value);
  }
}
