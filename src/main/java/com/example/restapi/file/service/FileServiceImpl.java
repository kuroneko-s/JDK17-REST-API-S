package com.example.restapi.file.service;

import com.example.restapi.file.model.FileSaveParams;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Validated
public class FileServiceImpl implements FileService {
    final String DEFAULT_PATH = System.getProperty("user.dir");

    @Override
    public void save(@Valid FileSaveParams params) {
        String pathStr = DEFAULT_PATH + File.separator + params.getPackageName();

        File file = new File(pathStr);

        if (!file.exists()) { // 파일 유무 확인
            this.createFolder(pathStr);
        } else if (file.exists() && !file.isDirectory()) { // 파일 타입 확인
            // 폴더가 아닐 경우 삭제.
            if (!file.delete()) {
                throw new RuntimeException(pathStr + " 파일 삭제 실패");
            }

            this.createFolder(pathStr);
        }

        // template 저장.
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(pathStr + File.separator + params.getFileName())
        ) {
            fileOutputStream.write(params.getTemplate().getBytes());
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(pathStr + File.separator + params.getFileName() + " File Not Found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 폴더 생성.
     * @param pathStr 생성 위치
     */
    private void createFolder(String pathStr) {
        try {
            Path path = Paths.get(pathStr);
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
