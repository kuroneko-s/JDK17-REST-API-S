package com.example.restapi.automatic.model.templates;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BeforeTemplateParams {
    private boolean isDI = false;
    private String className;

    public BeforeTemplateParams(String className) {
        this(className, false);
    }

    public BeforeTemplateParams(String className, boolean isDI) {
        if (className == null || className.isBlank()) {
            throw new IllegalArgumentException(className);
        }

        this.isDI = isDI;
        this.className = className;
    }
}
