package com.example.restapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

@Slf4j
@JsonComponent // object Mapper에 Type 등록
public class ErrorsSerializer extends JsonSerializer<Errors> {
    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        errors.getFieldErrors().forEach(e -> {
            try {
                gen.writeStartObject();

                gen.writeStringField("field", e.getField());
                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());
                Object rejectedValue = e.getRejectedValue();

                if (rejectedValue != null) {
                    gen.writeStringField("rejectedValue", rejectedValue.toString());
                }

                gen.writeEndObject();
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        });

        errors.getGlobalErrors().forEach(e -> {
            try {
                gen.writeStartObject();

                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());

                gen.writeEndObject();
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        });

        gen.writeEndArray();
    }
}
