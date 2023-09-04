package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.templates.GetTemplateType;
import com.example.restapi.automatic.model.templates.DefaultTemplateForController;
import com.example.restapi.automatic.model.templates.GetTemplateForController;

import java.util.List;

import static com.example.restapi.util.ConstantValues.*;

public class ControllerTemplatesCreatorServiceImpl implements TemplatesCreatorService {
    private final GetTemplateForController getTemplate = GetTemplateForController.INSTANCE;
    private final DefaultTemplateForController defaultTemplate = DefaultTemplateForController.INSTANCE;

    @Override
    public String beforeTemplate(String... classNames) {
        return null;
    }

    /**
     * Controller의 getTemplate 작성
     *
     * @param params       package, method 이름에 사용됨.
     * @param templateType {@link GetTemplateType}
     * @return bind 된 getTemplate
     */
    @Override
    public String getTemplate(String params, GetTemplateType templateType) {
        String packageName = params.toLowerCase();
        String methodName;
        String parameterClassName;
        String serviceName = params.substring(0, 1).toLowerCase() + params.substring(1) + "Service";

        if (templateType == GetTemplateType.LIST) {
            methodName = "get" + params + SUBFIX_LIST;
            parameterClassName = "Get" + params + SUBFIX_LIST_PARAMS;
        } else {
            methodName = "get" + params + SUBFIX_DETAIL;
            parameterClassName = params + SUBFIX_DETAIL_PARAMS;
        }

        return getTemplate.bind(packageName, methodName, methodName, parameterClassName, serviceName, methodName);
    }

    /**
     * Controller의 regTemplate 작성
     *
     * @param params package, method 이름에 사용됨.
     * @return bind 된 regTemplate
     */
    @Override
    public String regTemplate(String params) {
        String packageName = params.toLowerCase();
        String methodName = "reg" + params;
        String parameterClassName = "Reg" + params + "Params";
        String serviceName = params.substring(0, 1).toLowerCase() + params.substring(1) + "Service";

        return defaultTemplate.bind(packageName, methodName, methodName, parameterClassName, serviceName, methodName);
    }

    /**
     * Controller의 modTemplate 작성
     *
     * @param params package, method 이름에 사용됨.
     * @return bind 된 regTemplate
     */
    @Override
    public String modTemplate(String params) {
        String packageName = params.toLowerCase();
        String methodName = "mod" + params;
        String parameterClassName = "Mod" + params + "Params";
        String serviceName = params.substring(0, 1).toLowerCase() + params.substring(1) + "Service";

        return defaultTemplate.bind(packageName, methodName, methodName, parameterClassName, serviceName, methodName);
    }

    /**
     * Controller의 delTemplate 작성
     *
     * @param params package, method 이름에 사용됨.
     * @return bind 된 regTemplate
     */
    @Override
    public String delTemplate(String params) {
        String packageName = params.toLowerCase();
        String methodName = "del" + params;
        String parameterClassName = "Del" + params + "Params";
        String serviceName = params.substring(0, 1).toLowerCase() + params.substring(1) + "Service";

        return defaultTemplate.bind(packageName, methodName, methodName, parameterClassName, serviceName, methodName);
    }

    /**
     * Controller 클래스 생성
     * @param templates 내부 메서드 templates
     * @param params 클래스 명
     * @return 클래스 Template
     */
    @Override
    public String fileTemplate(List<String> templates, String params) {
        StringBuilder stringBuilder = new StringBuilder();
        String className = params + "Controller";
        String serviceClassName = params + "Service";
        String serviceName = serviceClassName.substring(0, 1).toLowerCase() + serviceClassName.substring(1);

        stringBuilder.append("@Controller\n");
        stringBuilder.append("class ").append(className).append("{ \n");

        stringBuilder.append("@Autowired\n");
        stringBuilder.append("private ").append(serviceClassName).append(" ").append(serviceName).append(";\n");
        stringBuilder.append("\n");

        templates.forEach(template -> stringBuilder.append(template).append("\n"));

        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
