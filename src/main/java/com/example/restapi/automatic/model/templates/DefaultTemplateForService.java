package com.example.restapi.automatic.model.templates;

import lombok.Getter;

@Getter
public enum DefaultTemplateForService implements Template {
    INSTANCE;

    // 모든 동작의 반환값은 void ( 문제가 있으면 에러를 발생시키는게 기본 전제 )
    // insert 과정에서 Builder로 진행했었지..
    // 뼈대만 작성하고 내부 파라미터들은 수동으로 작성하게끔

    private final String template = "@Override\n" +
            "public void {1}({2} params) { \n" +
            "\t{3} {4} = {5}.Builder().build();\n" +
            "\t{6};\n" +
            "} \n";
}
