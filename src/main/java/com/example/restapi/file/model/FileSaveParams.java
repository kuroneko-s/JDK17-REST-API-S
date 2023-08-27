package com.example.restapi.file.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileSaveParams {
    @NotNull(message = "빈값안돼!")
    @NotEmpty(message = "빈값안된다고!")
    private String template;

    @NotNull(message = "빈값안돼!")
    @NotEmpty(message = "빈값안된다고!")
    private String fileName;

    @NotNull(message = "빈값안돼!")
    @NotEmpty(message = "빈값안된다고!")
    private String packageName;
}
