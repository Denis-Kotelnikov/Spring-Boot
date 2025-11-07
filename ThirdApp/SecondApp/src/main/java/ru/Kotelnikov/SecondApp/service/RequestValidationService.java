package ru.Kotelnikov.SecondApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.Kotelnikov.SecondApp.exception.ValidationFailedException;

@Service
@Slf4j
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage()))
                    .reduce((s1, s2) -> s1 + "; " + s2)
                    .orElse("Validation failed");
            log.error("Validation errors: {}", errorMessage);
            throw new ValidationFailedException(errorMessage);
        }
    }
}
