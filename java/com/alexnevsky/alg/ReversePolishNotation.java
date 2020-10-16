package com.alexnevsky.alg;

import java.util.Stack;

/**
 * @author Alex Nevsky
 *
 * Date: 05/10/2020
 */
public class ReversePolishNotation {

  public static final int OPERATION_STRING_MAX_LENGTH = 1;
  public static final String EMPTY_STRING = "";
  public static final String ADDITION_OPERATION_CHAR_STRING = "+";
  public static final String SUBTRACTION_OPERATION_CHAR_STRING = "-";
  public static final String MULTIPLICATION_OPERATION_CHAR_STRING = "*";
  public static final String DIVISION_OPERATION_CHAR_STRING = "/";
  public static final char ADDITION_OPERATION_CHAR = '+';
  public static final char SUBTRACTION_OPERATION_CHAR = '-';
  public static final char MULTIPLICATION_OPERATION_CHAR = '*';
  public static final char DIVISION_OPERATION_CHAR = '/';

  public static double evaluate(String expr) {
    if (EMPTY_STRING.equals(expr)) {
      return 0;
    }

    final Stack<String> stack = new Stack<>();
    final String[] parts = expr.split(" ");
    for (String s : parts) {
      if (isValidOperation(s)) {
        double d1 = Double.parseDouble(stack.pop());
        double d2 = Double.parseDouble(stack.pop());
        stack.push(calculate(d1, d2, s));
      } else {
        stack.push(s);
      }
    }

    return Double.parseDouble(stack.pop());
  }

  private static String calculate(double d1, double d2, String s) {
    if (ADDITION_OPERATION_CHAR_STRING.equals(s)) {
      return String.valueOf(d2 + d1);
    }
    if (SUBTRACTION_OPERATION_CHAR_STRING.equals(s)) {
      return String.valueOf(d2 - d1);
    }
    if (MULTIPLICATION_OPERATION_CHAR_STRING.equals(s)) {
      return String.valueOf(d2 * d1);
    }
    if (DIVISION_OPERATION_CHAR_STRING.equals(s)) {
      return String.valueOf(d2 / d1);
    }
    throw new UnsupportedOperationException(s);
  }

  public static boolean isValidOperation(String s) {
    if (s.length() != OPERATION_STRING_MAX_LENGTH) {
      return false;
    }
    char c = s.toCharArray()[0];
    return (int) c == ADDITION_OPERATION_CHAR
        || (int) c == SUBTRACTION_OPERATION_CHAR
        || (int) c == MULTIPLICATION_OPERATION_CHAR
        || (int) c == DIVISION_OPERATION_CHAR ;
  }

  public static void main(String[] args) {
    System.out.println("hi");
    System.out.println(evaluate("5 1 2 + 4 * + 3 -")); // 14
  }
}
