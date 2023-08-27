package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.GetTemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplatesCreatorServiceImpl implements TemplatesCreatorService {
    private final String getTemplate = "@RequestMapping(value = \"/{1}/{2}\")\n" +
            "public ResponseEntity {3}({4} params) {\n" +
            "\t return ResponseEntity.ok({5}.{6}(params));\n" +
            "}";

    private final String defaultTemplate = "@RequestMapping(value = \"/{1}/{2}\")\n" +
            "public ResponseEntity {3}({4} params) {\n" +
            "\t {5}.{6}(params);\n" +
            "\t return ResponseEntity.ok();\n" +
            "}";

    /**
     * Controller의 getTemplate 작성
     *
     * @param params       package, method 이름에 사용됨.
     * @param templateType {@link GetTemplateType}
     * @return bind 된 getTemplate
     */
    @Override
    public String getTemplateForController(String params, GetTemplateType templateType) {
        String packageName = params.toLowerCase();
        String methodName;
        String parameterClassName;
        String serviceName = params.substring(0, 1).toLowerCase() + params.substring(1) + "Service";

        if (templateType == GetTemplateType.DEFAULT) {
            methodName = "get" + params;
            parameterClassName = "Get" + params + "Params";
        } else if (templateType == GetTemplateType.LIST) {
            methodName = "get" + params + "List";
            parameterClassName = "Get" + params + "ListParams";
        } else {
            methodName = "get" + params + "ByKey";
            parameterClassName = "Get" + params + "ByKeyParams";
        }

        return this.bind(getTemplate, packageName, methodName, methodName, parameterClassName, serviceName, methodName);
    }

    /**
     * Controller의 regTemplate 작성
     *
     * @param params package, method 이름에 사용됨.
     * @return bind 된 regTemplate
     */
    @Override
    public String regTemplateForController(String params) {
        String packageName = params.toLowerCase();
        String methodName = "reg" + params;
        String parameterClassName = "Reg" + params + "Params";
        String serviceName = params.substring(0, 1).toLowerCase() + params.substring(1) + "Service";

        return this.bind(defaultTemplate, packageName, methodName, methodName, parameterClassName, serviceName, methodName);
    }

    /**
     * Controller의 modTemplate 작성
     *
     * @param params package, method 이름에 사용됨.
     * @return bind 된 regTemplate
     */
    @Override
    public String modTemplateForController(String params) {
        String packageName = params.toLowerCase();
        String methodName = "mod" + params;
        String parameterClassName = "Mod" + params + "Params";
        String serviceName = params.substring(0, 1).toLowerCase() + params.substring(1) + "Service";

        return this.bind(defaultTemplate, packageName, methodName, methodName, parameterClassName, serviceName, methodName);
    }

    /**
     * Controller의 delTemplate 작성
     *
     * @param params package, method 이름에 사용됨.
     * @return bind 된 regTemplate
     */
    @Override
    public String delTemplateForController(String params) {
        String packageName = params.toLowerCase();
        String methodName = "del" + params;
        String parameterClassName = "Del" + params + "Params";
        String serviceName = params.substring(0, 1).toLowerCase() + params.substring(1) + "Service";

        return this.bind(defaultTemplate, packageName, methodName, methodName, parameterClassName, serviceName, methodName);
    }


    @Override
    public String fileTemplateForController(List<String> templates, String params) {
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
