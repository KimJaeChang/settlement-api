package kr.co.kjc.settlement.domain.payment.toss_payments.repository;

public interface PaymentRepository {

  void findAll();

  void findOne();

  void save();

  void saveHistory();

  void update();

  void delete();
}
