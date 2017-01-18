package main

import (
  "fmt"
  "github.com/anevsky/algorithms/search"
)

func main() {
    fmt.Printf("hello, world\n")

    a := []int{9, 4, 2, 8, 3, 5, 7, 10, 1, 6}
    fmt.Println(a)

    search.SentinelLinearSearch(a, 10, 1)
}
