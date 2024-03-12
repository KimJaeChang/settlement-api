package kr.co.kjc.settlement.domain.common.repository;

public interface PaymentRepository {

  void findAll();

  void findOne();

  void save();

  void saveHistory();

  void update();

  void delete();
}
