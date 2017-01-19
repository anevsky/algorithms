package search

import "fmt"

func SentinelLinearSearch(A []int, n int, x int) int {
    fmt.Printf("Hello, SentinelLinearSearch!\n")
    fmt.Println("Input: ", A, n, x)

    zn := n - 1

    last := A[zn]
    A[zn] = x
    i := 0
    for ; A[i] != x; i++ {
      // ok
    }
    A[zn] = last

    var result int
    if i < zn || A[zn] == x {
        result = i
    } else {
      result = -1
    }

    fmt.Printf("Result: element %v has index %v\n", x, result)

    return result
}
