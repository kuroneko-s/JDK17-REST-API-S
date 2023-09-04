package com.example.restapi.automatic.model.templates;

import lombok.Getter;

@Getter
public enum DefaultTemplateForController implements Template {
    INSTANCE;

    private final String template = "@RequestMapping(value = \"/{1}/{2}\")\n" +
            "public ResponseEntity {3}({4} params) {\n" +
            "\t {5}.{6}(params);\n" +
            "\t return ResponseEntity.ok();\n" +
            "}";
}
