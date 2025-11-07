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
import ru.Kotelnikov.SecondApp.service.ModifyResponseService;
import ru.Kotelnikov.SecondApp.service.ValidationService;
import ru.Kotelnikov.SecondApp.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {
        long currentTime = java.lang.System.currentTimeMillis();
        long timeDiff = currentTime - request.getReceivedTime();
        log.info("Time difference from Service 1 receipt to Service 2 receipt: {} ms", timeDiff);

        log.info("request:{}", request);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        if ("123".equals(request.getUid())) {
            log.error("UID 123 is unsupported for request: {}", request);
            throw new UnsupportedCodeException("UID 123 is unsupported");
        }

        log.info("Creating response for request: {}", request);
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemName(request.getSystemName())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("Created response: {}", response);

        try {
            log.info("Validating request: {}", request);
            validationService.isValid(bindingResult);
            log.info("Request validated successfully: {}", request);
        } catch (ValidationFailedException e) {
            log.error("Validation failed for request: {}. Error: {}", request, e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Modified response after validation failure: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("Unsupported code exception for request: {}. Error: {}", request, e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("Modified response after unsupported code exception: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error for request: {}. Error: {}", request, e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Modified response after unexpected error: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Modifying response: {}", response);
        modifyResponseService.modify(response);
        log.info("Modified response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}