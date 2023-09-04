package com.example.restapi;

import com.example.restapi.automatic.model.templates.GetTemplateType;
import com.example.restapi.automatic.service.ControllerTemplatesCreatorServiceImpl;
import com.example.restapi.file.model.FileSaveParams;
import com.example.restapi.file.service.FileService;
import com.example.restapi.file.service.FileServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class FileOutputTest {

    @Test
    @DisplayName("파일 저장 테스트")
    void fileOutputTest() {
        ControllerTemplatesCreatorServiceImpl creatorService = new ControllerTemplatesCreatorServiceImpl();
        String booksTemplate = creatorService.getTemplate("Books", GetTemplateType.DETAIL);

        FileService fileService = new FileServiceImpl();
        FileSaveParams fileSaveParams = FileSaveParams.builder()
                .template(booksTemplate)
                .fileName("Books.java")
                .packageName("Books".toLowerCase())
                .build();
        fileService.save(fileSaveParams);
    }

    @Test
    @DisplayName("파일 경로 찾기 (1)")
    void pathTest_Paths() {
        Path currentPath = Paths.get("");
        String path = currentPath.toAbsolutePath().toString();
        System.out.println(path); // C:\Users\kuron\IdeaProjects\JDK17-REST-API-S
    }

    @Test
    @DisplayName("파일 경로 찾기 (2)")
    void pathTest_Property() {
        String path = System.getProperty("user.dir");
        System.out.println(path); // C:\Users\kuron\IdeaProjects\JDK17-REST-API-S
    }

    @Autowired
    private FileService fileService;

    @Test
    @DisplayName("method 파라미터 Validate Annotation 검증.")
    void parameterEmptyTest() {
        // 블로그 https://mangkyu.tistory.com/174
        // @Valid는 동작상 컨트롤러에서만 동작한다.
        assertThrows(ConstraintViolationException.class, () -> {
            FileSaveParams fileSaveParams = FileSaveParams.builder().build();
            fileService.save(fileSaveParams);
        });
    }
}
