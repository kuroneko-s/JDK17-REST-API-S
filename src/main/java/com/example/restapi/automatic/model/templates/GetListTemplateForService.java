package com.example.restapi.automatic.model.templates;

import lombok.Getter;

@Getter
public enum GetListTemplateForService implements Template {
    INSTANCE;

    // get(detail) => 하나의 객체만 리턴함.
    // get(list) => 리스트 형태의 객체 리턴.
    // builder name => insert type의 camelcase
    // insert 과정에서 Builder로 진행했었지..

    private final String transactionalTemplate = "@Transactional\n";
    private final String template = "@Override\n" +
            "public {1} {2}({3} params) { \n" +
            "\t{4} {5} = {6}.Builder().build();\n" +
            "\treturn {7};\n" +
            "} \n";

    public String addTransactional() {
        return transactionalTemplate + template;
    }
}
