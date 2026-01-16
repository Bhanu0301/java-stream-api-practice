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

