package ru.Kotelnikov.SecondApp.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.Kotelnikov.SecondApp.exception.UnsupportedCodeException;
import ru.Kotelnikov.SecondApp.exception.ValidationFailedException;
import ru.Kotelnikov.SecondApp.model.*;
import ru.Kotelnikov.SecondApp.service.ModifyRequestService;
import ru.Kotelnikov.SecondApp.service.ModifyResponseService;
import ru.Kotelnikov.SecondApp.service.ValidationService;
import ru.Kotelnikov.SecondApp.util.DateTimeUtil;

import java.lang.System;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifyOperationUidResponseService") ModifyResponseService modifyResponseService,
                        ModifyRequestService modifyRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) throws ValidationFailedException {
        setReceivedTime(request);
        log.info("request:{}", request);

        if (!isValidSystemTime(request)) {
            return handleInvalidSystemTime(request);
        }

        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException("UID 123 не поддерживается");
        }

        Response response = createInitialResponse(request);
        validateRequest(request, bindingResult, response);
        modifyResponseService.modify(response);
        modifyRequestService.modify(request);
        log.info("Modified response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void setReceivedTime(Request request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("CET"));
        try {
            Date fixedDate = sdf.parse("2025-09-20 22:28:00");
            request.setReceivedTime(fixedDate.getTime());
        } catch (ParseException e) {
            log.error("Не удалось разобрать фиксированную дату: {}", e.getMessage());
            request.setReceivedTime(System.currentTimeMillis());
        }
    }

    private boolean isValidSystemTime(Request request) {
        SimpleDateFormat checkSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        checkSdf.setLenient(false);
        try {
            checkSdf.parse(request.getSystemTime());
            return true;
        } catch (ParseException e) {
            log.error("Неверный формат systemTime: {}. Ожидаемый формат: yyyy-MM-dd HH:mm:ss", request.getSystemTime());
            return false;
        }
    }

    private ResponseEntity<Response> handleInvalidSystemTime(Request request) {
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemName(request.getSystemName())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.FAILED)
                .errorCode(ErrorCodes.VALIDATION_EXCEPTION)
                .errorMessage(ErrorMessages.INVALID_SYSTEM_TIME)
                .build();
        log.info("Модифицированный ответ после ошибки валидации systemTime: {}", response);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Response createInitialResponse(Request request) {
        log.info("Создание ответа для запроса: {}", request);
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemName(request.getSystemName())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("Созданный ответ: {}", response);
        return response;
    }

    private void validateRequest(Request request, BindingResult bindingResult, Response response) throws ValidationFailedException {
        try {
            log.info("Валидация запроса: {}", request);
            validationService.isValid(bindingResult);
            log.info("Запрос успешно валидирован: {}", request);
        } catch (ValidationFailedException e) {
            log.error("Ошибка валидации для запроса: {}. Ошибка: {}", request, e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            throw new ValidationFailedException(e.getMessage());
        } catch (UnsupportedCodeException e) {
            log.error("Исключение неподдерживаемого кода для запроса: {}. Ошибка: {}", request, e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            throw e;
        } catch (Exception e) {
            log.error("Неожиданная ошибка для запроса: {}. Ошибка: {}", request, e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            throw e;
        }
    }
}