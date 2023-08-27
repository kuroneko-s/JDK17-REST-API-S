package com.example.restapi.file.service;

import com.example.restapi.file.model.FileSaveParams;

import javax.validation.Valid;

public interface FileService {
    void save(@Valid FileSaveParams params);
}
