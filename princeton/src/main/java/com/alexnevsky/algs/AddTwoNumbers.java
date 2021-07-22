package com.alexnevsky.algs;

/**
 * @author Alex Nevsky
 *
 * Add Two Numbers
 * @link https://leetcode.com/problems/add-two-numbers/
 *
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each of their nodes contains a single digit.
 * Add the two numbers and return the sum as a linked list.
 *
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 *
 * Date: 15/06/2021
 */
public class AddTwoNumbers {

  public static void main(String[] args) {
    System.out.print("");
  }

  class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummyHead = new ListNode();
    ListNode p = l1;
    ListNode q = l2;
    ListNode curr = dummyHead;
    int carry = 0;
    while (p != null || q != null) {
      int x = (p != null) ? p.val : 0;
      int y = (q != null) ? q.val : 0;
      int sum = carry + x + y;
      carry = sum / 10;
      curr.next = new ListNode(sum % 10);
      curr = curr.next;
      if (p != null) p = p.next;
      if (q != null) q = q.next;
    }
    if (carry > 0) {
      curr.next = new ListNode(carry);
    }
    return dummyHead.next;
  }
}
