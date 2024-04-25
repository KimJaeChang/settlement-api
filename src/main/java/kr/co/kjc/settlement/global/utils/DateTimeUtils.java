package kr.co.kjc.settlement.global.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.experimental.UtilityClass;

/**
 * static 메소드만 존재하는(인스턴스 생성이 필요없는) 유틸성 클래스는 Spring Bean Component 형태로 정의하는 대신 Lombok @UtilityClass로
 * 정의하자
 */
@UtilityClass
public class DateTimeUtils {

  public static String formatDate(LocalDate date) {
    return date.format(DateTimeFormatter.ISO_DATE);
  }

  public static LocalDate parseDate(LocalDateTime dateTime) {
    return dateTime.toLocalDate();
  }

  public static LocalDate parseDate(String date) {
    return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
  }

  public static LocalDateTime parseDateTime(String date) {
    return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static String formatDateTime(LocalDateTime date) {
    return date.format(DateTimeFormatter.ISO_DATE_TIME);
  }

  public static LocalDateTime convertStartDateTime(String date) {
    String startDate = date + "T00:00:00";
    return LocalDateTime.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static LocalDateTime convertStartDateTime(LocalDateTime date) {
    return convertStartDateTime(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
  }

  public static LocalDateTime convertEndDateTime(String date) {
    String endDate = date + "T23:59:59";
    return LocalDateTime.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static LocalDateTime convertEndDateTime(LocalDateTime date) {
    return convertEndDateTime(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
  }

  public static LocalDate parseLocalDate(String date, String format) {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
  }

  public static String formatLocalDate(LocalDate date, String format) {
    return date.format(DateTimeFormatter.ofPattern(format));
  }

  public static LocalDateTime parseLocalDateTime(String date, String format) {
    return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
  }

  public static String formatLocalDateTime(LocalDateTime dateTime, String format) {
    return dateTime.format(DateTimeFormatter.ofPattern(format));
  }

  public static String convertToISO8601(LocalDateTime dateTime) {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00").format(dateTime);
  }

  public static Date nowDate() {
    return Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
  }

  public static Date afterDateMs(long afterMs) {
    return Timestamp.valueOf(
        LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusSeconds((afterMs / 1000) % 60));
  }
}
