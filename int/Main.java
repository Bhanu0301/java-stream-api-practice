import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6);
        //1. Find the sum of all numbers
        //Optional<Integer> sum = nums.stream().reduce((a,b)->a+b);
        int sum = nums.stream().reduce(0, Integer::sum);
        //System.out.println("Sum:" + sum.get());
        System.out.println("Sum :" + sum);

        //2.Print average of a list
        //double avg// = nums.stream().mapToInt(e->e).average().getAsDouble();
        double avg = nums.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        System.out.println("Average :" + avg);

        //3.Square each and every number
        List<Integer> squares = nums.stream().map(e->e*e).collect(Collectors.toList());
        System.out.println("Squares :" + squares);

        //4.Square each and every number, then get those squares greater than 10
        List<Integer> squaresGreaterThanTen = nums.stream()
                .map(e->e*e)
                .filter(a->a>10)
                .collect(Collectors.toList());
        System.out.println("Squares greater than 10 :" + squaresGreaterThanTen);

        //5.Square each and every number, then get those squares greater than 10, then find average
        double squaresGreaterThanTen_Average = nums.stream()
                .map(e->e*e)
                .filter(a->a>10)
                .mapToInt(Integer::intValue)
                        .average()
                .orElse(0.0);
        System.out.println("Average of Squares greater than 10 :" + squaresGreaterThanTen_Average);

        //6.Even nos
        List<Integer> even = nums.stream()
                .filter(e->e%2==0)
                .collect(Collectors.toList());
        System.out.println("Even nos: " + even);

        //7.Odd nos
        List<Integer> odd = nums.stream()
                .filter(e -> e%2!=0)
                .collect(Collectors.toList());
        System.out.println("Odd nos: "+ odd);

        List<Integer> list = Arrays.asList(2,332,343,642,234,223,2213,2, 343, 123);
        //8.Nos. with prefix 2 (both version correct)
//        List<Integer> numsWithPrefix2 = list.stream()
//                .map(e->String.valueOf(e))
//                .filter(e->e.startsWith("2"))
//                .map(e->Integer.valueOf(e))
//                .collect(Collectors.toList());
        List<Integer> numsWithPrefix2 = list.stream()
                .map(e->String.valueOf(e))
                .filter(e->e.startsWith("2"))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        System.out.println("Nums with prefix 2 :"+ numsWithPrefix2);

        //9.Print all duplicates
//        Set<Integer> duplicates = list.stream()
//                .filter(e->Collections.frequency(list, e)>1) //O(n)
//                .collect(Collectors.toSet()); //total TC : O(n^2)
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = list.stream()
                .filter(e->!seen.add(e)) //O(1)
                .collect(Collectors.toSet()); //total TC : O(n)
        System.out.println("Duplicates :"+ duplicates);

        //10. Find Max and Minimum no. from stream
        int max = list.stream().max(Comparator.comparing(Integer::valueOf)).get();
        int min = list.stream().min(Comparator.comparing(Integer::valueOf)).get();
        System.out.println("Max : "+max+" ,Min : "+min);

        //OR
        int maxVal = list.stream().max(Integer::compareTo).orElseThrow();
        int minVal = list.stream().min(Integer::compareTo).orElseThrow();

        System.out.println("Max : " + maxVal + " ,Min : " + minVal);

        //11. Sort nos. in asc and desc order
        List<Integer> asc = list.stream().sorted().collect(Collectors.toList());
        List<Integer> desc =  list.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        System.out.println("Ascending order :" + asc);
        System.out.println("Descending order :"+ desc);

        //12. Get the first 5 nos.
        List<Integer> firstFive = asc.stream()
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("First five elements :" + firstFive);

        //13. Sum of firstFive
        int firstFiveSum = asc.stream()
                .limit(5)
                .reduce(0, Integer::sum);
        System.out.println("Sum of First five elements :" + firstFiveSum);

        //14.Skip first five elements
        List<Integer> nextFive = asc.stream()
                .skip(5)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("Skipping first 5 elems :" + nextFive);

        //15. Second highest no.
        int secHighest = list.stream()
                .sorted(Collections.reverseOrder())
                .distinct()
                .skip(1)
                .findFirst()
                .get();
//        int secHighest = list.stream()
//                .sorted(Collections.reverseOrder())
//                .distinct()
//                .limit(2)
//                .skip(1)
//                .findFirst()
//                .get();
        System.out.println("Second highest :" + secHighest);
        //Parallel stream vs Sequential streams
        long[] numbers = new long[2000];
        for(int i = 0;i<numbers.length; i++){
            numbers[i] = i+1;
        }
        long startTime = System.currentTimeMillis();
        long factorialSumSeq = Arrays.stream(numbers)
                .map(Main::factorial)
                .sum();
        System.out.println(factorialSumSeq);
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken - Sequential :"+ (endTime - startTime)); //200-300ms

        startTime = System.currentTimeMillis();
        long factorialSumParallel = Arrays.stream(numbers)
                .parallel()   //Faster execution for complex operations
                .map(Main::factorial)
                .sum();
        System.out.println(factorialSumParallel);
        endTime = System.currentTimeMillis();

        System.out.println("Time taken - Parallel :"+ (endTime - startTime));  //30-60ms
    }
    public static long factorial(long n){
        return LongStream.rangeClosed(1,n)
                .reduce(1,(long a, long b)->(a*b));
    }
}
//reduce -> to give a single ans from list, return type is Optional<Integer>
//avg -> getAsDouble(), mapToInt(e->e) because average, min, max works on premitive values
// avoid get() to prevent NoSuchElementException for empty streams.