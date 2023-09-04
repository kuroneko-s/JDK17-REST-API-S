package com.example.restapi.automatic.model.templates;

import javax.validation.constraints.NotNull;

public enum Templates {
    INSTANCE;

    public String getTemplate(@NotNull TemplateType type) {
        switch (type) {
            case CONTROLLER_GET:
                return """
                        @RequestMapping(value = "/_/_")
                        public ResponseEntity _(_ params) {
                        \t return ResponseEntity.ok(_._(params));
                        }""";
            case CONTROLLER_REG:
            case CONTROLLER_MOD:
            case CONTROLLER_DEL:
                return """
                        @RequestMapping(value = "/_/_")
                        public ResponseEntity _(_ params) {
                        \t _._(params);
                        \t return ResponseEntity.ok();
                        }""";
            case CONTROLLER_FILE:
                return "";
            case SERVICE_GET_LIST:
                return """
                        @Override
                        public _ _(_ params) {\s
                        \t_ _ = _.Builder().build();
                        \treturn _;
                        }\s
                        """;
            case SERVICE_GET_DETAIL:
                return """
                        @Override
                        public _ _(_ params) {\s
                        \treturn _;
                        }\s
                        """;
            case SERVICE_REG:
            case SERVICE_MOD:
            case SERVICE_DEL:
                return """
                        @Override
                        public void _(_ params) {\s
                        \t_ _ = _.Builder().build();
                        \t_;
                        }\s
                        """;
            default:
                throw new IllegalArgumentException(type.name());
        }
    }

    public String bind(String template, String... args) {
        for (String arg : args) {
            template = template.replaceFirst("_", arg);
        }

        return template;
    }

    public String addTransactional(String template) {
        return "@Transactional\n" + template;
    }
}
