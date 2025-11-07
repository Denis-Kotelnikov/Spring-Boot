package ru.Kotelnikov.SecondApp.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    // Уникальный идентификатор запроса
    @NotBlank(message = "uid is mandatory")
    @Size(max = 32, message = "uid must be up to 32 characters")
    private String uid;

    // Уникальный идентификатор операции
    @NotBlank(message = "operationUid is mandatory")
    @Size(max = 32, message = "operationUid must be up to 32 characters")
    private String operationUid;

    // Наименование системы
    @NotNull(message = "systemName is mandatory")
    private System systemName;

    // Время системы в формате yyyy-MM-dd HH:mm:ss
    @NotBlank(message = "systemTime is mandatory")
    private String systemTime;

    // Источник запроса
    private String source;

    // Должность сотрудника
    private Position position;

    // Зарплата сотрудника
    private Double salary;

    // Бонус сотрудника
    private Double bonus;

    // Количество рабочих дней
    private Integer workDays;

    // Идентификатор коммуникации
    @NotNull(message = "communicationId is mandatory")
    @Min(value = 1, message = "communicationId must be at least 1")
    @Max(value = 100000, message = "communicationId must not exceed 100000")
    private Integer communicationId;

    // Идентификатор шаблона
    private Integer templateId;

    // Код продукта
    private Integer productCode;

    // Код SMS
    private Integer smsCode;

    // Время получения запроса
    private long receivedTime;

    @Override
    public String toString() {
        return "{" +
                "uid ='" + uid + '\'' +
                ", operationUid ='" + operationUid + '\'' +
                ", systemName ='" + systemName + '\'' +
                ", systemTime ='" + systemTime + '\'' +
                ", source ='" + source + '\'' +
                ", communicationId ='" + communicationId + '\'' +
                ", templatedId ='" + templateId + '\'' +
                ", productCode ='" + productCode + '\'' +
                ", smsCode ='" + smsCode + '\'' +
                ", receivedTime =" + receivedTime +
                '}';
    }
}