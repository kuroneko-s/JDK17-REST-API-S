package com.example.restapi.automatic.controller;

import com.example.restapi.automatic.model.RequestParams;
import com.example.restapi.automatic.model.marker.AdminValidationGroup;
import com.example.restapi.automatic.service.AutomaticService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AutomaticController {

    private final AutomaticService automaticService;

    public AutomaticController(AutomaticService automaticService) {
        this.automaticService = automaticService;
    }

    @GetMapping("/create")
    public ResponseEntity createClasses(@RequestBody @Validated(AdminValidationGroup.class) RequestParams params) {
        automaticService.creator(params);
        return ResponseEntity.ok().build();
    }
}
