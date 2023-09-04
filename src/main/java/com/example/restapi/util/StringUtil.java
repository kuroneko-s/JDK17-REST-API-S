package com.example.restapi.util;

import static com.example.restapi.util.ConstantValues.SUBFIX_LIST;

/**
 * 문자열 가공 유틸
 * @author donghyuk
 */
public class StringUtil {
    /**
     * 스네이크 케이스, 파스칼 케이스를 카멜케이스로 변환 후 반환해준다.
     * @param target 스네이크 케이스 | 파스칼 케이스의 문자열.
     * @param type {@link StringType}
     * @return 카멜 케이스의 문자열
     */
    public static String getCamelCase(String target, StringType type) {
        if (type == StringType.SNAKE) {
            if (!target.contains("_")) {
                throw new IllegalArgumentException(target);
            }

            StringBuilder stringBuilder = new StringBuilder();
            String[] strings = target.split("_");
            int len = strings.length;

            // 첫글자 소문자.
            stringBuilder.append(strings[0].substring(0, 1).toLowerCase())
                    .append(strings[0].substring(1));

            for (int i = 1; i < len; i++) {
                String str = strings[i];
                String camel = str.substring(0, 1);
                stringBuilder.append(camel.toUpperCase()).append(str.substring(1));
            }

            return stringBuilder.toString();
        } else if (type == StringType.PASCAL) {
            if (target.contains("_")) {
                throw new IllegalArgumentException(target);
            }

            String firstStr = target.substring(0, 1);
            return firstStr.toLowerCase() + target.substring(1);
        } else {
            if (target.contains("_")) {
                throw new IllegalArgumentException(target);
            }

            if (target.charAt(0) < 97) {
                throw new IllegalArgumentException(target);
            }

            return target;
        }
    }
}
