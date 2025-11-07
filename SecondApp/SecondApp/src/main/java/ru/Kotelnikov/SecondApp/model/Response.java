package ru.Kotelnikov.SecondApp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    // Уникальный идентификатор ответа
    private String uid;

    // Уникальный идентификатор операции
    private String operationUid;

    // Наименование системы
    private System systemName;

    // Время системы в формате yyyy-MM-dd HH:mm:ss
    private String systemTime;

    // Код результата
    private Codes code;

    // Годовая премия
    private Double annualBonus;

    // Код ошибки
    private ErrorCodes errorCode;

    // Сообщение об ошибке
    private ErrorMessages errorMessage;
}
