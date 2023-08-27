package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.GetTemplateType;

import java.util.Arrays;
import java.util.List;

public interface TemplatesCreatorService {
    default String bind(String template, String... args) {
        for (int i = 1; i <= args.length; i++) {
            template = template.replace("{" + i + "}", args[i - 1]);
        }

        return template;
    }

    String getTemplateForController(String params, GetTemplateType templateType);

    String regTemplateForController(String params);

    String modTemplateForController(String params);

    String delTemplateForController(String params);

    String fileTemplateForController(List<String> templates, String className);
}
