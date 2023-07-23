package com.example.restapi;

import jdk.dynalink.Operation;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FunctionalTest {


    class Employee {
        String name;

        public Employee(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Employee employee = (Employee) o;
            return Objects.equals(name, employee.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    class Product {
        private String name;
        private int price;

        public Product(int price, String name) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    @Test
    public void test() {
        Consumer<String> consumer = s -> System.out.println(s.split(" ")[0]);
        consumer.andThen(System.out::println).accept("Hello World");

        Function<String, Integer> function = String::length;

        Integer helloWorld = function.apply("Hello World");

        List<String> list = Arrays.asList("Java", "Scala", "Groovy", "Python", "Go", "Swift");

        // duck type
        var collect = list.stream()
                .sorted(Comparator.reverseOrder())
                .distinct()
                .toList();

        var sum = IntStream.range(0, 10)
                .filter(v -> v % 2 == 0)
                .sorted()
                .sum();

        Employee employee1 = new Employee("Test1");
        Employee employee2 = new Employee("Test1");

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        List<Employee> employeeResultList = employeeList.stream().distinct().toList();

        IntStream.of(1, 2, 3, 4, 5, 6)
                // .peek(System.out::println)
                .sum();

        String collect1 = Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(value -> value + "aa")
                .collect(Collectors.joining(", ", "<", ">"));

        System.out.println(collect1);

        List<Product> productList = Arrays.asList(
                new Product(23, "potatoes"),
                new Product(14, "orange"),
                new Product(13, "lemon"),
                new Product(23, "bread"),
                new Product(13, "sugar"));

        IntSummaryStatistics collect2 = productList.stream()
                .collect(Collectors.summarizingInt(Product::getPrice));

        IntSummaryStatistics collect3 = IntStream.range(1, 100)
                .boxed()
                .collect(Collectors.summarizingInt(value -> value));

        // .collect(Collectors.summarizingInt(value -> Integer.valueOf(value)));
        IntSummaryStatistics collect4 = IntStream.range(0, 1000).boxed().collect(Collectors.summarizingInt(value -> value));
        System.out.println(collect4);

        IntStream.range(1, 100)
                .map(operand -> {
                    System.out.println(operand);
                    return operand;
                })
                .peek(System.out::println);

        Map<Boolean, List<Product>> collect5 = productList.stream()
                .collect(Collectors.partitioningBy(product -> product.getPrice() > 15));

        System.out.println(collect5);
    }

    @Test
    void test2() {
        List<Student> students = Arrays.asList(
                new Student(80, 90, 75),
                new Student(70, 100, 75),
                new Student(85, 90, 85),
                new Student(80, 100, 90)
        );

        students.stream()
                .flatMapToInt(student -> IntStream.of(student.getKor(), student.getEng(), student.getMath()))
                .average()
                .ifPresent(avg -> System.out.println(Math.round(avg * 10) / 10.0));

        IntStream.range(1, 4)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);

        int reduceResult = IntStream.range(1, 4)
                .reduce(10, Integer::sum);

        System.out.println(reduceResult);

        Integer combinerWasCalled = 10 + Stream.of(1, 2, 3)
                .parallel()
                .reduce(0, (a, b) -> {
                    System.out.println("is run ? ");
                    return a + b;
                }, (a, b) -> {
                    System.out.println("combiner was called");
                    return a + b;
                });

        System.out.println(combinerWasCalled);

        Student student = new Student(10, 20, 30);
        student.setName("student1");

        Optional<String> sample = Optional.ofNullable(student.getName());
        // String name = sample.orElse("Anonymous");
        String name = sample.orElseThrow(() -> new IllegalArgumentException("is none"));
        System.out.println(name);

        Stream<String> stringStream = Optional
                .ofNullable(student.getName())
                .map(s -> s.split(""))
                .map(Arrays::stream)
                .orElseGet(Stream::empty);


        sample.orElse("anonymous");
        sample.orElseGet(() -> "anonymous");

    }

    public <T> Stream<T> collectionToStream(Collection<T> collection) {
        return Optional
                .ofNullable(collection)
                .map(Collection::stream)
                .orElseGet(Stream::empty);
    }

    @Test
    public void test3() {
        findUserEmailOrElse();

        findUserEmailOrElseGet();

        Optional.empty()
                .ifPresentOrElse(System.out::println, () -> System.out.println("is empty"));

        Map<String, String> testMap = new HashMap<>();
        testMap.put("test1", "donghyuk");

        String value = testMap.getOrDefault("test1", "default value");

        /*Stream.of("a", "b", "c", "d", "e", "f")
                .filter(s -> {
                    System.out.println("filter - " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach : " + s));*/

        Stream.of("a", "b", "c", "d", "e", "f")
                .filter(s -> {
                    System.out.println("filter - " + s);
                    return true;
                }).toList()
                .stream().forEach(s -> System.out.println("forEach : " + s));

    }

    @Test
    public void test4() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
//        System.out.println(forkJoinPool);


        Arrays.asList("a", "b", "c", "d", "e")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n", s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));



         // Arrays.parallelSort();


    }

    public void findUserEmailOrElse() {
        String userEmail = "Empty";
        String result = Optional.ofNullable(userEmail)
                .orElse(getUserEmail());

        System.out.println(result);
    }

    public void findUserEmailOrElseGet() {
        String userEmail = "Empty";
        String result = Optional.ofNullable(userEmail)
                .orElseGet(this::getUserEmail);

        System.out.println(result);
    }

    private String getUserEmail() {
        System.out.println("getUserEmail() Called");
        return "mangkyu@tistory.com";
    }
}

class Student {

    private int kor;
    private int eng;
    private int math;
    private String name;

    public Student(int kor, int eng, int math) {
        this.kor = kor;
        this.eng = eng;
        this.math = math;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKor() {
        return kor;
    }

    public int getEng() {
        return eng;
    }

    public int getMath() {
        return math;
    }

    @Test
    public void test5() {
        Thread thread = new Thread();
        thread.start();
    }

}

@FunctionalInterface
interface CustomLamdaFunction {
    int max(int a, int b);
}