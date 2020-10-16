package com.alexnevsky.alg;

/**
 * @author Alex Nevsky
 *
 * Date: 05/10/2020
 */
public class NumberToOrdinal {

  public static final String ZERO_CHAR_STRING = "0";

  public static String numberToOrdinal( Integer number ) {
    if (0 == number) {
      return ZERO_CHAR_STRING;
    }

    char[] sym = number.toString().toCharArray();
    String ending = getEnding(number, sym);

    return number.toString() + ending;
  }

  private static String getEnding(int n, char[] sym) {
    if (sym.length == 1) {
      if (n == 1) {
        return "st";
      } else if (n == 2) {
        return "nd";
      } else if (n == 3) {
        return "rd";
      } else {
        return "th";
      }
    }

    String lastTwoChars = String.valueOf(sym, sym.length - 2, 2);
    if ("11".equals(lastTwoChars) || "12".equals(lastTwoChars) || "13".equals(lastTwoChars)) {
      return "th";
    }

    if (lastTwoChars.endsWith("1")) {
      return "st";
    } else if (lastTwoChars.endsWith("2")) {
      return "nd";
    } else if (lastTwoChars.endsWith("3")) {
      return "rd";
    } else {
      return "th";
    }
  }

  public static void main(String[] args) {
    System.out.println("hi");
    System.out.println(numberToOrdinal(112));
  }
}
