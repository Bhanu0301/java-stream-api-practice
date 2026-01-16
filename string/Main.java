import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        // String Stream Practice
        List<String> list = Arrays.asList("bhanu","vikram","anu");

        //1.convert into uppercase
        List<String> upperCase = list.stream()
//                .map(name->name.toUpperCase())
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Upper-case list : " + upperCase);

        List<List<String>> nest = Arrays.asList(
                Arrays.asList("a","b"),
                Arrays.asList("c","d"),
                Arrays.asList("e","f")
        );
        //2.Print all in uppercase
        nest.stream()
                .flatMap(l->l.stream())
                .map(str->str.toUpperCase())
                .forEach(System.out::println);

        List<String> flatNest = nest.stream()
                .flatMap(l->l.stream())
                .collect(Collectors.toList());

        System.out.println("flat nest : " + flatNest);

        List<String> fruits = Arrays.asList("apple","mango","orange","banana","kiwi","pineapple");
        //3.sort the fruits based on their length

//        List<String> sortedFruits = fruits.stream()
//                .sorted((a,b)->a.length()-b.length())
//                .collect(Collectors.toList());
        List<String> sortedFruits = fruits.stream()
                .sorted(Comparator.comparingInt(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
        System.out.println(sortedFruits);
    }
}

