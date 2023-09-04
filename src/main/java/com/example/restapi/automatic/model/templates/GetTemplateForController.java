package com.example.restapi.automatic.model.templates;

import lombok.Getter;

@Getter
public enum GetTemplateForController implements Template {
    INSTANCE;

    private final String template = "@RequestMapping(value = \"/{1}/{2}\")\n" +
            "public ResponseEntity {3}({4} params) {\n" +
            "\t return ResponseEntity.ok({5}.{6}(params));\n" +
            "}";
}
