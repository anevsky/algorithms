package com.alexnevsky.algs;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Maximum Number of Events That Can Be Scheduled
 *
 * You are going to schedule presentation timeline. You have only 1 room to present event.
 * Find max number of event that possible to schedule.
 *
 * Given 2 arrays: 1st with event start time, 2nd with duration of corresponded event.
 *
 * arrivals [1, 3, 3, 5, 7]
 * durations [2, 2, 3, 2, 2]
 *
 * 1 <= n <= 50 number of events
 * 1 <= d <= 10000 duration of event
 *
 * timeline
 * _____1_____2_____3_____4_____5_____5_____7_____8_____9_____10
 *
 * events
 *      1_____2_____3
 *                  3_____4_____5
 *                  3_____4_____5_____6
 *                              5_____6_____7
 *                                          7_____8_____9
 *
 * result: 4
 *
 * explanation:
 * we have 2 events at 3 PM, only 1 can be scheduled to maximize number of events.
 *
 * @author Alex Nevsky
 *
 * Date: 21/08/2021
 */
public class MaxEvents {

  public static void main(String[] args) {
    System.out.printf("maxEvents: %s", MaxEvents.maxEvents(
        List.of(1, 3, 3, 5, 7),
        List.of(2, 2, 2, 2, 2)
    ));
  }

  /**
   * Priority queue pq keeps the current open events.
   *
   * Iterate from events,
   * For each, we add new events starting on time d to the queue pq.
   * Also, we remove the events that longer then next event start time.
   *
   * Then we greedily schedule event that ends soonest.
   * If we can schedule event, we increment the counter.
   *
   * @param arrivals events start time
   * @param durations event duration
   * @return max possible events one by one
   */
  // NOTE: solution might be not correct! not tested on real tests
  public static int maxEvents(List<Integer> arrivals, List<Integer> durations) {
    int n = arrivals.size();

    int[][] A = new int[n][2];
    for (int i = 0; i < n; ++i) {
      A[i][0] = arrivals.get(i);
      A[i][1] = arrivals.get(i) + durations.get(i);
    }

    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int count = 0;
    int i = 0;
    int d = 0;
    while (!pq.isEmpty() || i < n) {
      if (pq.isEmpty()) d = A[i][0];
      while (i < n && A[i][0] <= d) pq.offer(A[i++][1]);
      pq.poll();
      ++d;
      ++count;
      while (!pq.isEmpty() && pq.peek() > d) pq.poll();
    }
    return count;
  }

}
