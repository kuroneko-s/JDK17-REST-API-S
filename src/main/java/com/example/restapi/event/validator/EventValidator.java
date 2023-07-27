package com.example.restapi.event.validator;

import com.example.restapi.event.domain.EventDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() &&
                eventDto.getMaxPrice() >= 0) {
            errors.rejectValue("basePrice", "valueError", "wrong basePrice value.");
            errors.rejectValue("maxPrice", "valueError", "wrong maxPrice value.");
            errors.reject("wrongPrice", "all price value error");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if (
                endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())
        ) {
            errors.rejectValue("endEventDateTime", "DateError", "date value error");
        }
    }
}
