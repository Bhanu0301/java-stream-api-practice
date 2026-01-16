# Java Stream API

The **Stream API** (introduced in Java 8) provides a functional and declarative way to process collections of data.

---

## 🔹 What is a Stream?

- Is **not** a data structure  
- Does **not** store elements  
- Operates on a data source (Collection, Array, I/O, etc.)  
- Supports **pipeline processing**  
- Is **lazy**, meaning it executes only when a terminal operation is invoked  

Streams allow developers to write cleaner, more readable, and concise code using lambda expressions and functional-style operations.

---

## 🔹 Stream Pipeline

A stream pipeline consists of:

1. **Source** – Provides data  
2. **Intermediate operations** – Transform data  
3. **Terminal operation** – Produces result and triggers execution  

**Example:**
```java
list.stream()
    .filter(x -> x > 10)
    .map(x -> x * 2)
    .collect(Collectors.toList());
```
## 🔹 Intermediate Operations

Intermediate operations transform a stream and return another stream.

### Key Characteristics
- Lazy (not executed immediately)  
- Can be chained  
- Executed only when a terminal operation is called  

### Common Intermediate Operations
- `filter(Predicate)` – Filters elements based on a condition  
- `map(Function)` – Transforms each element  
- `flatMap(Function)` – Flattens nested streams  
- `distinct()` – Removes duplicate elements  
- `sorted()` – Sorts elements  
- `limit(long)` – Limits number of elements  
- `skip(long)` – Skips first n elements  
- `peek(Consumer)` – Performs an action without modifying the stream (mainly for debugging)  

**Example:**
```java
stream.filter(x -> x > 5)
      .map(x -> x * 2)
      .sorted();
```
## 🔹 Terminal Operations

Terminal operations end the stream pipeline and trigger execution.

### Key Characteristics
- Produce a result or side-effect  
- Stream cannot be reused after a terminal operation  
- Execute all intermediate operations  

### Common Terminal Operations
- `forEach(Consumer)` – Performs an action on each element  
- `collect(Collector)` – Converts stream into a collection or map  
- `reduce(BinaryOperator)` – Reduces elements to a single value  
- `count()` – Returns number of elements  
- `findFirst()` – Returns first element  
- `findAny()` – Returns any element  
- `anyMatch()` – Checks if any element matches condition  
- `allMatch()` – Checks if all elements match condition  
- `noneMatch()` – Checks if no elements match condition  

**Example:**
```java
long count = list.stream()
                 .filter(x -> x > 10)
                 .count();
```
## 🔹 Lazy Evaluation

Intermediate operations are not executed immediately.  
Execution starts only when a terminal operation is called.

**Example:**
```java
stream.filter(x -> {
    System.out.println("filter");
    return x > 5;
});
```

## 🔹 Collectors: groupingBy and partitioningBy

### ✅ groupingBy

`groupingBy` groups elements of a stream based on a classifier function.

- Default result: `Map<K, List<T>>`
- Variants: can use downstream collectors (e.g., `counting`, `summingInt`, etc.)

**Example:**
```java
import java.util.*;
import java.util.stream.*;

public class GroupingExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Anna");

        // Group names by first letter
        Map<Character, List<String>> groupedByFirstLetter =
                names.stream()
                     .collect(Collectors.groupingBy(name -> name.charAt(0)));

        System.out.println(groupedByFirstLetter);
        // Output: {A=[Alice, Anna], B=[Bob], C=[Charlie], D=[David]}

        // Count names by first letter
        Map<Character, Long> countByFirstLetter =
                names.stream()
                     .collect(Collectors.groupingBy(name -> name.charAt(0), Collectors.counting()));

        System.out.println(countByFirstLetter);
        // Output: {A=2, B=1, C=1, D=1}
    }
}
```

---

### ✅ partitioningBy

`partitioningBy` divides elements into two groups based on a predicate.

- Result: `Map<Boolean, List<T>>`
- Keys: `true` → elements matching predicate, `false` → elements not matching

**Example:**
```java
import java.util.*;
import java.util.stream.*;

public class PartitioningExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);

        // Partition numbers into even and odd
        Map<Boolean, List<Integer>> partitioned =
                numbers.stream()
                       .collect(Collectors.partitioningBy(n -> n % 2 == 0));

        System.out.println(partitioned);
        // Output: {false=[1, 3, 5, 7, 9], true=[2, 4, 6, 8]}

        // Count numbers in each partition
        Map<Boolean, Long> countPartitioned =
                numbers.stream()
                       .collect(Collectors.partitioningBy(n -> n % 2 == 0, Collectors.counting()));

        System.out.println(countPartitioned);
        // Output: {false=5, true=4}
    }
}
```

---

### 🔹 Key Differences

| Feature          | groupingBy                           | partitioningBy                  |
|------------------|--------------------------------------|---------------------------------|
| Input            | Classifier function                  | Predicate (boolean condition)   |
| Output           | `Map<K, List<T>>` (or other collector)| `Map<Boolean, List<T>>`         |
| Number of groups | Many (depends on classifier)         | Exactly 2 (`true` and `false`)  |
| Use case         | Categorize into multiple buckets     | Split into two categories       |

```
## 🔹 Parallel Streams

A parallel stream processes elements concurrently using multiple threads.  
It internally uses the **ForkJoinPool** to divide the data and process it in parallel.

### Creating a Parallel Stream
```java
list.parallelStream();
```
or
```java
list.stream().parallel();
```

### ✅ When to Use Parallel Streams
- Dataset is large  
- Operations are CPU-intensive  
- Tasks are independent and stateless  
- Order of execution does not matter  
- System has multiple CPU cores  

**Example:**
```java
list.parallelStream()
    .map(this::compute)
    .collect(Collectors.toList());
```

### ❌ When NOT to Use Parallel Streams
- Dataset is small  
- Operations involve I/O (database, network calls)  
- Shared mutable state is involved  
- Order of elements is important  
- Synchronization is required  

**Bad Practice Example:**
```java
list.parallelStream().forEach(sharedList::add);
```

---

## 🔹 Stream vs Parallel Stream

| Feature       | Stream          | Parallel Stream        |
|---------------|-----------------|------------------------|
| Execution     | Single thread   | Multiple threads       |
| Order         | Maintained      | Not guaranteed         |
| Performance   | Predictable     | Faster for CPU tasks   |
| Complexity    | Simple          | Requires caution       |

