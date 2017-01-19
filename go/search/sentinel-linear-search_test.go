package search

import "testing"

func TestSentinelLinearSearch(t *testing.T) {
    a := []int{9, 4, 2, 8, 3, 5, 7, 10, 1, 6}

    r1 := SentinelLinearSearch(a, 10, 6)
    if r1 != 9 {
      t.Error("Expected 9, got ", r1)
    }

    r2 := SentinelLinearSearch(a, 10, 9)
    if r2 != 0 {
      t.Error("Expected 0, got ", r2)
    }

    r3 := SentinelLinearSearch(a, 10, 8)
    if r3 != 3 {
      t.Error("Expected 0, got ", r3)
    }
}
