package kr.co.kjc.settlement.service;

public interface RedisService {

  Boolean save(String table, String key, Object value);

}
