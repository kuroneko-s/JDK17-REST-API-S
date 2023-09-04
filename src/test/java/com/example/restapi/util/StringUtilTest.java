package com.example.restapi.util;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {
    @Test
    @DisplayName("스네이크 케이스를 카멜 케이스로 변환")
    void snakeToCamelTest() {
        String targetMessage = "snake_case_sample_test_strings_is_this_s_first";
        String result = StringUtil.getCamelCase(targetMessage, StringType.SNAKE);

        assertNotNull(result);
        assertNotEquals(targetMessage, result);
        assertFalse(result.contains("_"));

        System.out.println(result);
    }

    @Test
    @DisplayName("스네이크 케이스 값을 카멜 케이스 타입으로 요청할 경우")
    void snakeToCamelTest_Error_Req_Camel() {
        String targetMessage = "snake_case_sample_test_strings_is_this_s_first";

        assertThrows(IllegalArgumentException.class, () -> {
            String result = StringUtil.getCamelCase(targetMessage, StringType.CAMEL);
        });
    }

    @Test
    @DisplayName("스네이크 케이스 값을 파스칼 케이스 타입으로 요청할 경우")
    void snakeToCamelTest_Error_Req_Pascal() {
        String targetMessage = "snake_case_sample_test_strings_is_this_s_first";

        assertThrows(IllegalArgumentException.class, () -> {
            String result = StringUtil.getCamelCase(targetMessage, StringType.PASCAL);
        });
    }

    @Test
    @DisplayName("카멜 케이스를 카멜 케이스로 변환")
    void camelToCamelTest() {
        String targetMessage = "snakeCaseSampleTestStringsIsThisSFirst";
        String result = StringUtil.getCamelCase(targetMessage, StringType.CAMEL);

        assertNotNull(result);
        assertEquals(targetMessage, result);
        assertFalse(result.contains("_"));

        System.out.println(result);
    }

    @Test
    @DisplayName("카멜 케이스 값을 스네이크 타입으로 요청할 경우")
    void camelToCamelTest_Error_Req_Snake() {
        String targetMessage = "snakeCaseSampleTestStringsIsThisSFirst";

        assertThrows(IllegalArgumentException.class, () -> {
            String result = StringUtil.getCamelCase(targetMessage, StringType.SNAKE);
        });
    }

    // 카멜 케이스나 파스칼 케이스나 차이점이 첫글자 뿐이고, 카멜케이스 만드는 과정 자체는 첫글자를 소문자로 바꾸는 과정이니깐 동일한 결과를 가져다 준다.
    // 그러면 이 두 값을 구분할 필요가 있느냐라고 한다면 구분을 하는게 좋다. 엄연히 다른 타입이기 떄문이다. (추후 수정 가능성)
    @Test
    @DisplayName("카멜 케이스 값을 파스칼 타입으로 요청할 경우")
    void camelToCamelTest_Error_Req_Pascal() {
        String targetMessage = "snakeCaseSampleTestStringsIsThisSFirst";

        String result = StringUtil.getCamelCase(targetMessage, StringType.PASCAL);

        assertNotNull(result);
        assertEquals(targetMessage, result);
        assertFalse(result.contains("_"));

        System.out.println(result);
    }

    @Test
    @DisplayName("파스칼 케이스를 카멜 케이스로 변환")
    void pascalToCamelTest() {
        String targetMessage = "SnakeCaseSampleTestStringsIsThisSFirst";
        String result = StringUtil.getCamelCase(targetMessage, StringType.PASCAL);

        assertNotNull(result);
        assertNotEquals(targetMessage, result);
        assertFalse(result.contains("_"));

        System.out.println(result);
    }

    @Test
    @DisplayName("파스칼 케이스 값을 스네이트 타입으로 요청할 경우")
    void pascalToCamelTest_Error_Req_Snake() {
        String targetMessage = "SnakeCaseSampleTestStringsIsThisSFirst";

        assertThrows(IllegalArgumentException.class, () -> {
            String result = StringUtil.getCamelCase(targetMessage, StringType.SNAKE);
        });
    }

    @Test
    @DisplayName("파스칼 케이스 값을 카멜 케이스 타입으로 요청할 경우")
    void pascalToCamelTest_Error_Req_Camel() {
        String targetMessage = "SnakeCaseSampleTestStringsIsThisSFirst";

        assertThrows(IllegalArgumentException.class, () -> {
            String result = StringUtil.getCamelCase(targetMessage, StringType.CAMEL);
        });
    }
}