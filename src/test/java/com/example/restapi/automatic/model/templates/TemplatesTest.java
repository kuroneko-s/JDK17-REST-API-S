package com.example.restapi.automatic.model.templates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemplatesTest {
    @Test
    public void nullCheck() {
        Templates templates = Templates.INSTANCE;

        assertThrows(NullPointerException.class, () -> {
            String template = templates.getTemplate(null);
        });
    }

}