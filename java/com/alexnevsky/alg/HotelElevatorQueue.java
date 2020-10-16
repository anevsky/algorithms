package com.alexnevsky.alg;

import java.util.*;

/**
 * Created by Alex Nevsky on 1/28/17.
 *
 * Hotel with 0..M floors, queue of N people (A[K] - weight, B[K] - target floor),
 * elevator with X people capacity and Y weight limit.
 *
 * Find how many times the elevator will stop.
 *
 * People in the queue could not change the place. Back to the 0 floor also should be counted.
 */
public class HotelElevatorQueue {

    public int solution(int[] A, int[] B, int M, int X, int Y) {
        int stops = 0;

        int peopleNum = A.length;

        Queue<People> peopleQueue = new LinkedList<>();
        for (int i = 0; i < peopleNum; ++i) {
            peopleQueue.add(new People(A[i], B[i]));
        }

        Elevator elevator = new Elevator(X, Y);

        boolean result = true;
        while (result) {
            People p = peopleQueue.poll();
            if (p != null) {
                boolean hasCapacity = elevator.addPeople(p);
                if (!hasCapacity) {
                    stops += elevator.process();
                    elevator.reset();
                    elevator.addPeople(p);
                }

            } else {
                // last time
                stops += elevator.process();

                result = false;
            }
        }

        return stops;
    }

    class Elevator {
        private int capacity;
        private long limit;
        private List<People> peoples = new ArrayList<>();

        public Elevator() {
        }

        public Elevator(int capacity, long limit) {
            this.capacity = capacity;
            this.limit = limit;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public long getLimit() {
            return limit;
        }

        public void setLimit(long limit) {
            this.limit = limit;
        }

        public boolean addPeople(People p) {
            return peoples.size() <= capacity && limit >= currentWeight() + p.getWeight() && peoples.add(p);
        }

        public void reset() {
            peoples = new ArrayList<>();
        }

        private long currentWeight() {
            long w = 0;

            for (People p : peoples) {
                w += p.getWeight();
            }

            return w;
        }

        public int process() {
            Set<Integer> floors = new HashSet<>();
            for (People p : peoples) {
                floors.add(p.getTargetFloor());
            }
            return floors.size() + 1;
        }
    }

    class People {
        private int weight;
        private int targetFloor;

        public People() {
        }

        public People(int weight, int targetFloor) {
            this.weight = weight;
            this.targetFloor = targetFloor;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getTargetFloor() {
            return targetFloor;
        }

        public void setTargetFloor(int targetFloor) {
            this.targetFloor = targetFloor;
        }
    }

}
