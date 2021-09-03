package com.alexnevsky.algs;

import java.util.Stack;

/**
 * @author Alex Nevsky
 *
 * Date: 27/08/2021
 */
public class BalancedBrackets {

  public static void main(String[] args) {
    String s = "{{[[((])]]}}";
    System.out.printf("isBalanced: %s%n", isBalanced(s));
  }

  public static String isBalanced(String s) {
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < s.length(); ++i) {
      char c = s.charAt(i);
      if (c == '{' || c == '[' || c == '(') {
        stack.push(c);
      } else {
        if (stack.isEmpty()) return "NO";
        char p = stack.pop();
        if (c == '}' && p != '{') return "NO";
        if (c == ']' && p != '[') return "NO";
        if (c == ')' && p != '(') return "NO";
      }
    }
    if (stack.isEmpty()) return "YES";
    else return "NO";
  }
}
