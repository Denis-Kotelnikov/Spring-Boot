package ru.Kotelnikov.SecondApp.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.Kotelnikov.SecondApp.exception.ValidationFailedException;

@Service
public interface ValidationService {
    void isValid (BindingResult bindingResult) throws ValidationFailedException;
}
