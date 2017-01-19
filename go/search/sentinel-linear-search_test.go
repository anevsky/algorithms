package search

import "testing"

func TestSentinelLinearSearch(t *testing.T) {
    t.Log("Test last, first and middle elements (first element has index 0).")

    a := []int{9, 4, 2, 8, 3, 5, 7, 10, 1, 6}

    i1 := SentinelLinearSearch(a, 10, 6)
    if i1 != 9 {
      t.Error("Expected 9, got", i1)
    }

    i2 := SentinelLinearSearch(a, 10, 9)
    if i2 != 0 {
      t.Error("Expected 0, got", i2)
    }

    i3 := SentinelLinearSearch(a, 10, 8)
    if i3 != 3 {
      t.Error("Expected 0, got", i3)
    }
}
