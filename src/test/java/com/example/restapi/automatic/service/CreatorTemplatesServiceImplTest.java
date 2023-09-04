package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.templates.GetTemplateType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ActiveProfiles("test")
class CreatorTemplatesServiceImplTest {
    private TemplatesCreatorService service = new ControllerTemplatesCreatorServiceImpl();

    @Test
    @DisplayName("getTemplate 생성. (리스트)")
    public void getTemplate_LIST_test() {
        String template = service.getTemplate("Books", GetTemplateType.LIST);

        System.out.println(template);
    }

    @Test
    @DisplayName("getTemplate 생성. (상세)")
    public void getTemplate_DETAIL_test() {
        String template = service.getTemplate("Books", GetTemplateType.DETAIL);

        System.out.println(template);
    }
    
    @Test
    @DisplayName("regTemplate 생성.")
    public void regTemplate_test() {
        String template = service.regTemplate("Books");

        System.out.println(template);
    }

    @Test
    @DisplayName("modTemplate 생성.")
    void modTemplate_test() {
        String template = service.modTemplate("Books");

        System.out.println(template);
    }

    @Test
    @DisplayName("delTemplate 생성.")
    void delTemplate_test() {
        String template = service.delTemplate("Books");

        System.out.println(template);
    }

    @Test
    @DisplayName("class Template 생성.")
    void fileTemplateForController_test() {
        List<String> templateList = new ArrayList<>();
        templateList.add(service.getTemplate("Books", GetTemplateType.LIST));
        templateList.add(service.getTemplate("Books", GetTemplateType.DETAIL));
        templateList.add(service.regTemplate("Books"));
        templateList.add(service.modTemplate("Books"));
        templateList.add(service.delTemplate("Books"));

        String template = service.fileTemplate(templateList, "Books");

        System.out.println(template);
    }
}