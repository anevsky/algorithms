package com.alexnevsky.alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Alex Nevsky on 1/30/17.
 *
 * Find all anagrams of provided word.
 *
 * art, tar, rat
 * aba, aab, baa
 *
 * dictionary=[cart, art, tar, horse, aab, aba], word=rat, result=[art, tar]
 *
 * art, tar = true
 * car, tar = false
 * bba, aab = false
 * horse, dog = false
 *
 *
 * isAnagram("a", "aa") = ?
 * isAnagram("bba", "aab") = ?
 * "aaa" "aaa"
 */
public class AnagramsOfWord {

    // complexity n
    public static List<String> solution(List<String> dictionary, String word) {
        List<String> result = new ArrayList<>();

        for (String w : dictionary) {
            if (isAnagram(w, word)) {
                result.add(w);
            }
        }

        return result;
    }

    // complexity n^2
    public static boolean isAnagram(String a, String b) {
        boolean result = false;

        if (a.equals(b)) {
            return false;
        }

//        if (a.length() == b.length()) {
//            boolean isSameLetters = true;
//
//            char[] letters = a.toCharArray();
//            for(int i = 0; i < letters.length && isSameLetters; ++i) {
//                // count that every letter occurs the same number of times in each word
//                isSameLetters = StringUtils.countMatches(a, letters[i]) == StringUtils.countMatches(b, letters[i]);
//            }
//
//            if (isSameLetters) {
//                Set<Character> aSet = new HashSet<>(a.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
//                Set<Character> bSet = new HashSet<>(b.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
//                // check if words have the same letters
//                result = aSet.equals(bSet);
//            }
//        }

        return result;
    }
}
