package com.alexnevsky.alg;

/**
 * @author Alex Nevsky
 *
 * Date: 05/10/2020
 */
public class CreditCard {

  public static final int ALLOWED_NO_MASK_LENGTH = 5;
  public static final int MASK_FROM_INDEX = 1;
  public static final int MASK_BEFORE_LAST_INDEX = 4;
  public static final char MASK_SYMBOL = '#';
  public static final String EMPTY_STRING = "";

  public static String maskify(String creditCardNumber) {
    if (EMPTY_STRING.equals(creditCardNumber)) {
      return EMPTY_STRING;
    }

    if (creditCardNumber.length() <= ALLOWED_NO_MASK_LENGTH) {
      return creditCardNumber;
    }

    char[] sym = creditCardNumber.toCharArray();
    for (int i = MASK_FROM_INDEX; i < sym.length - MASK_BEFORE_LAST_INDEX; ++i) {
      if (isDigit(sym[i])) {
        sym[i] = MASK_SYMBOL;
      }
    }

    return String.valueOf(sym);
  }

  public static boolean isDigit(char c) {
    return (int) c >= '0' && (int) c <= '9';
  }

  public static void main(String[] args) {
    System.out.println("hi");
    System.out.println(maskify("4556-3646-0793-5616")); // 4###-####-####-5616
    System.out.println(maskify("A1234567BCDEFG89HI")); // A#######BCDEFG89HI

  }
}
