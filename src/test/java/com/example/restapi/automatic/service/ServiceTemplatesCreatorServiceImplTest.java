package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.templates.GetTemplateType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceTemplatesCreatorServiceImplTest {
    private final ServiceTemplatesCreatorServiceImpl service = new ServiceTemplatesCreatorServiceImpl();

    @Test
    @DisplayName("Service 템플릿 상세조회")
    void getTemplateDetailTest() {
        String template = service.getTemplate("Books", GetTemplateType.DETAIL);

        System.out.println(template);
    }

    @Test
    @DisplayName("Service 템플릿 목록조회")
    void getTemplateListTest() {
        String template = service.getTemplate("Books", GetTemplateType.LIST);

        System.out.println(template);
    }

    @Test
    @DisplayName("Service 등록 템플릿")
    void regTemplateTest() {
        String template = service.regTemplate("Books");

        System.out.println(template);
    }

    @Test
    @DisplayName("Service 수정 템플릿")
    void modTemplateTest() {
        String template = service.modTemplate("Books");

        System.out.println(template);
    }

    @Test
    @DisplayName("Service 삭제 템플릿")
    void delTemplateTest() {
        String template = service.delTemplate("Books");

        System.out.println(template);
    }
}