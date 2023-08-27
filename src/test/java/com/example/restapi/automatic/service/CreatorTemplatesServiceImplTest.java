package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.GetTemplateType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CreatorTemplatesServiceImplTest {
    @Autowired
    private TemplatesCreatorService service;

    @Test
    @DisplayName("getTemplate 생성. (기본)")
    public void getTemplate_DEFAULT_test() {
        String template = service.getTemplateForController("Books", GetTemplateType.DEFAULT);

        System.out.println(template);
    }

    @Test
    @DisplayName("getTemplate 생성. (리스트)")
    public void getTemplate_LIST_test() {
        String template = service.getTemplateForController("Books", GetTemplateType.LIST);

        System.out.println(template);
    }

    @Test
    @DisplayName("getTemplate 생성. (상세)")
    public void getTemplate_DETAIL_test() {
        String template = service.getTemplateForController("Books", GetTemplateType.DETAIL);

        System.out.println(template);
    }
    
    @Test
    @DisplayName("regTemplate 생성.")
    public void regTemplate_test() {
        String template = service.regTemplateForController("Books");

        System.out.println(template);
    }

    @Test
    @DisplayName("modTemplate 생성.")
    void modTemplate_test() {
        String template = service.modTemplateForController("Books");

        System.out.println(template);
    }

    @Test
    @DisplayName("delTemplate 생성.")
    void delTemplate_test() {
        String template = service.delTemplateForController("Books");

        System.out.println(template);
    }

    @Test
    @DisplayName("class Template 생성.")
    void fileTemplateForController_test() {
        List<String> templateList = new ArrayList<>();
        templateList.add(service.getTemplateForController("Books", GetTemplateType.DEFAULT));
        templateList.add(service.getTemplateForController("Books", GetTemplateType.LIST));
        templateList.add(service.getTemplateForController("Books", GetTemplateType.DETAIL));
        templateList.add(service.regTemplateForController("Books"));
        templateList.add(service.modTemplateForController("Books"));
        templateList.add(service.delTemplateForController("Books"));

        String template = service.fileTemplateForController(templateList, "Books");

        System.out.println(template);
    }

}