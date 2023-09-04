package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.templates.GetTemplateType;

import java.util.List;

/**
 * 기본 Spring import 같은 경우는 기본 패키지 기반으로 참조를 함.
 * 기본 패키지를 알지 못하는 상황에선 추가할 수 없음.
 * TODO: API 레벨에서 기본 패키지를 받아온다면 첨부를 할 수 있게끔 ?
 */

public interface TemplatesCreatorService {
    String beforeTemplate(String... classNames);

    String getTemplate(String params, GetTemplateType templateType);

    String regTemplate(String params);

    String modTemplate(String params);

    String delTemplate(String params);

    String fileTemplate(List<String> templates, String className);
}
