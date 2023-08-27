package com.example.restapi.automatic.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class RequestParamsTest {

    @Test
    public void getValueOfKey_Test() {
        HashMap<String, Object> targetMap = new HashMap<>();
        targetMap.put("Books", new String[]{"C", "R", "U", "D"});
        targetMap.put("User", new String[]{"C", "R", "U", "D"});
        targetMap.put("Admin", new String[]{"C", "R", "U", "D"});
        targetMap.put("Author", new String[]{"C", "R", "U"});

        RequestParams requestParams = new RequestParams();
        requestParams.setParams(targetMap);

        List<String> books = requestParams.getValueOfKey("Books");
        System.out.println(books);

        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    public void getValueOfKey_Empty_Test() {
        HashMap<String, Object> targetMap = new HashMap<>();
        RequestParams requestParams = new RequestParams();
        requestParams.setParams(targetMap);

        List<String> books = requestParams.getValueOfKey("Books");
        System.out.println(books);

        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

}