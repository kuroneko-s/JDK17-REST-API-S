package com.example.restapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;

import javax.print.attribute.IntegerSyntax;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;



class AppTests {

    @Test
    void contextLoads() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");

        Stream<String> listStream = list.stream();
        // CrudRepository

//        Observable
//        Observer
    }


}
