package com.alexnevsky.alg;

/**
 * @author Alex Nevsky
 *
 * Date: 30/08/2020
 */
public class CardsGame {

  public static void main(String[] args) {
    System.out.println(solution("A4Q9J5", "K2Q9J5"));
  }

  public static int solution(String A, String B) {
    char[] a = A.toCharArray();
    char[] b = B.toCharArray();

    int[] ai = new int[a.length];
    int[] bi = new int[b.length];
    for (int i = 0; i < a.length; ++i) {
      ai[i] = getInt(a[i]);
      bi[i] = getInt(b[i]);
    }

    int aWins = 0;
    for (int i = 0; i < ai.length; ++i) {
      if (ai[i] > bi[i]) {
        ++aWins;
      }
    }

    return aWins;
  }

  private static int getInt(char c) {
    switch (c) {
      case 'T': return 10;
      case 'J': return 11;
      case 'Q': return 12;
      case 'K': return 13;
      case 'A': return 14;
      default: return c - 100;
    }
  }
}
