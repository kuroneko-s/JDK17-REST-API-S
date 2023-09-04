package com.example.restapi.automatic.service;

import com.example.restapi.automatic.model.templates.GetTemplateType;
import com.example.restapi.automatic.model.RequestParams;
import com.example.restapi.file.model.FileSaveParams;
import com.example.restapi.file.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AutomaticServiceImpl implements AutomaticService {
    private final TemplatesCreatorService templatesService;
    private final FileService fileService;

    public AutomaticServiceImpl(TemplatesCreatorService templatesService, FileService fileService) {
        this.templatesService = templatesService;
        this.fileService = fileService;
    }

    @Override
    public void creator(RequestParams params) {
        params.getKeys().forEach(key -> {
            // 하나의 객체 생성 시작

            // 메서드 템플릿 생성
            List<String> templateList = params.getValueOfKey(key).stream().map(value -> switch (value) {
                        case "C" -> templatesService.regTemplate(key);
                        case "RL" -> templatesService.getTemplate(key, GetTemplateType.LIST);
                        case "RK" -> templatesService.getTemplate(key, GetTemplateType.DETAIL);
                        case "U" -> templatesService.modTemplate(key);
                        case "D" -> templatesService.delTemplate(key);
                        default -> null;
                    })
                    .filter(Objects::nonNull)
                    .toList();

            // 파일 템플릿 생성
            String fileTemplate = templatesService.fileTemplate(templateList, key);

            // 파일 저장
            FileSaveParams fileSaveParams = FileSaveParams.builder()
                    .template(fileTemplate)
                    .fileName(key + ".java")
                    .packageName(key.toLowerCase())
                    .build();
            fileService.save(fileSaveParams);

            // 하나의 객체 생성 종료
        });
    }
}
