package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.templates.GetTemplateType;
import com.example.restapi.automatic.model.templates.TemplateType;
import com.example.restapi.automatic.model.templates.Templates;
import com.example.restapi.util.StringType;
import com.example.restapi.util.StringUtil;

import java.util.List;

import static com.example.restapi.util.ConstantValues.*;

public class ServiceTemplatesCreatorServiceImpl implements TemplatesCreatorService {
    private Templates templates = Templates.INSTANCE;

    @Override
    public String beforeTemplate(String... classNames) {
        return null;
    }

    /**
     * Service 조회 메서드 템플릿
     * TODO : 지금 호출하는 구조가 클래스별로 선언해야하는데 이걸 좋은 방향으로 수정할 수 있는지 확인 필요함.
     * @param params
     * @param templateType
     * @return
     */
    @Override
    public String getTemplate(String params, GetTemplateType templateType) {
        String returnType = params; // Entity (Table Name)
        String methodName;
        String parameterClassName;
        String returnValue = StringUtil.getCamelCase(params, StringType.PASCAL) + "Mapper.select" + params;
        String builderClassName = "Select" + params + "Params";
        String builderVariableName = builderClassName.substring(0, 1).toLowerCase() + builderClassName.substring(1);

        if (templateType == GetTemplateType.DETAIL) {
            methodName = "get" + params + SUBFIX_DETAIL;
            parameterClassName = params + SUBFIX_DETAIL_PARAMS;
            returnValue = returnValue + SUBFIX_DETAIL + "(params)";

            String template = templates.getTemplate(TemplateType.SERVICE_GET_DETAIL);

            return templates.bind(template, returnType, methodName, parameterClassName, builderClassName, builderVariableName, builderClassName, returnValue);
        } else { // LIST
            methodName = "get" + params + SUBFIX_LIST;
            parameterClassName = "Get" + params + SUBFIX_LIST_PARAMS;
            returnValue = returnValue + SUBFIX_LIST + "(params)";

            String template = templates.getTemplate(TemplateType.SERVICE_GET_LIST);

            return templates.bind(template, returnType, methodName, parameterClassName, builderClassName, builderVariableName, builderClassName, returnValue);
        }
    }

    /**
     * Service 등록 메서드 템플릿
     * @param params
     * @return
     */
    @Override
    public String regTemplate(String params) {
        String methodName = "reg" + params;
        String parameterClassName = "Reg" + params + "Params";
        String calledMethod = StringUtil.getCamelCase(params, StringType.PASCAL) + "Mapper.insert" + params + "(params)";
        String builderClassName = "Insert" + params + "Params";
        String builderVariableName = builderClassName.substring(0, 1).toLowerCase() + builderClassName.substring(1);

        String template = templates.getTemplate(TemplateType.SERVICE_REG);
        return templates.bind(template, methodName, parameterClassName, builderClassName, builderVariableName, builderClassName, calledMethod);
    }

    /**
     * Service 수정 메서드 템플릿
     * @param params
     * @return
     */
    @Override
    public String modTemplate(String params) {
        String methodName = "mod" + params;
        String parameterClassName = "Mod" + params + "Params";
        String calledMethod = StringUtil.getCamelCase(params, StringType.PASCAL) + "Mapper.update" + params + "(params)";
        String builderClassName = "Update" + params + "Params";
        String builderVariableName = builderClassName.substring(0, 1).toLowerCase() + builderClassName.substring(1);

        String template = templates.getTemplate(TemplateType.SERVICE_MOD);
        return templates.bind(template, methodName, parameterClassName, builderClassName, builderVariableName, builderClassName, calledMethod);
    }

    /**
     * Service 삭제 메서드 템플릿
     * @param params
     * @return
     */
    @Override
    public String delTemplate(String params) {
        String methodName = "del" + params;
        String parameterClassName = "Del" + params + "Params";
        String calledMethod = StringUtil.getCamelCase(params, StringType.PASCAL) + "Mapper.delete" + params + "(params)";
        String builderClassName = "Delete" + params + "Params";
        String builderVariableName = builderClassName.substring(0, 1).toLowerCase() + builderClassName.substring(1);

        String template = templates.getTemplate(TemplateType.SERVICE_DEL);
        return templates.bind(template, methodName, parameterClassName, builderClassName, builderVariableName, builderClassName, calledMethod);
    }

    /**
     * Service 전체 템플릿
     * @param templates
     * @param className
     * @return
     */
    @Override
    public String fileTemplate(List<String> templates, String className) {
        return null;
    }
}
