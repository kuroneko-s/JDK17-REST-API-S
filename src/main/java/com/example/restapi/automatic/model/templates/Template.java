package com.example.restapi.automatic.model.templates;

public interface Template {
    String getTemplate();

    default String bind(String... args) {
        var template = getTemplate();

        for (int i = 1; i <= args.length; i++) {
            template = template.replace("{" + i + "}", args[i - 1]);
        }

        return template;
    }

    default String addTransactional() {
        return "@Transactional\n" + getTemplate();
    }
}

