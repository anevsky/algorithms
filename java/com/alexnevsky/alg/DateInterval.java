package com.alexnevsky.alg;

import com.alexnevsky.alg.HotelElevatorQueue.People;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alex Nevsky
 *
 * Date: 02/06/2021
 *
 * Миша работает в команде Яндекс.Маркета, которая предоставляет производителям товаров аналитику
 * о продажах. Сейчас Миша разбирается с периодизацией: нужно собирать данные по дням, неделям,
 * месяцам, кварталам и годам. От клиентов приходят запросы, в которых указан период детализации
 * и интервал: начальная и конечная даты. Так что первоначально Мише нужно разбить интервал на
 * периоды. Так, если клиент хочет данные с 2020-01-10 по 2020-03-25 с детализацией по месяцам,
 * то ему вернутся данные за три периода: c 2020-01-10 по 2020-01-31, с 2020-02-01 по 2020-02-29
 * и с 2020-03-01 по 2020-03-25.
 *
 * Example:
 * MONTH
 * 2020-01-10 2020-03-25
 *
 * Result:
 * 3
 * 2020-01-10 2020-01-31
 * 2020-02-01 2020-02-29
 * 2020-03-01 2020-03-25
 */

// WRONG !!!
public class DateInterval {

  public static void main(String[] args) throws Exception {
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

    String type = r.readLine();
    String interval = r.readLine();

    String[] dates = interval.split(" ");
    Instant startDate = Instant.parse(dates[0]);
    Instant endDate = Instant.parse(dates[1]);
    Instant end = startDate;

    long amountToAdd = 1;
    TemporalUnit tu = ChronoUnit.DAYS;

    if ("WEEK".equals(type)) {
      tu = ChronoUnit.WEEKS;
      int dayNum = startDate.get(ChronoField.DAY_OF_WEEK);
      end = end.plus(7 % dayNum, ChronoUnit.DAYS);
    } else if ("MONTH ".equals(type)) {
      tu = ChronoUnit.MONTHS;
      int dayNum = startDate.get(ChronoField.DAY_OF_MONTH);
      startDate.get(ChronoField.DAY_OF_MONTH);
      end = end.plus(7 % dayNum, ChronoUnit.DAYS);
    } else if ("QUARTER".equals(type)) {
      amountToAdd = 3;
      tu = ChronoUnit.MONTHS;
    } else if ("YEAR".equals(type)) {
      tu = ChronoUnit.YEARS;
    } else if ("REVIEW".equals(type)) {
      amountToAdd = 6;
      tu = ChronoUnit.MONTHS;
    }

    long count = 0;
    List<String> result = new ArrayList<>();
    Instant tmp;
    while (end.isBefore(endDate)) {
      result.add(String.format("%s %s", startDate, end));
      tmp = startDate;
      startDate = end;
      end = tmp.plus(amountToAdd, tu);
      ++count;
    }

    System.out.println(count);
    for (String str : result) {
      System.out.println(str);
    }
  }
}
