package search

import "fmt"

func SentinelLinearSearch(A []int, n int, x int) int {
    fmt.Printf("hello, search\n")
    fmt.Println("input: ", A, n, x)

    last := A[n - 1]
    A[n - 1] = x
    i := 1
    for ; A[i - 1] != x; i++ {
      //
    }
    A[n - 1] = last

    var result int
    if i < n || A[n - 1] == x {
        result = i - 1
    } else {
      result = -1
    }

    fmt.Printf("element %v has index %v\n", x, result)

    return result
}
