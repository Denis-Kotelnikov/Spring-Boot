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

    @NotBlank(message = "uid is mandatory")
    @Size(max = 32, message = "uid must be up to 32 characters")
    private String uid;

    @NotBlank(message = "operationUid is mandatory")
    @Size(max = 32, message = "operationUid must be up to 32 characters")
    private String operationUid;

    @NotNull(message = "systemName is mandatory")
    private System systemName;

    @NotBlank(message = "systemTime is mandatory")
    private String systemTime;

    private String source;

    @NotNull(message = "communicationId is mandatory")
    @Min(value = 1, message = "communicationId must be at least 1")
    @Max(value = 100000, message = "communicationId must not exceed 100000")
    private Integer communicationId;

    private Integer templateId;
    private Integer productCode;
    private Integer smsCode;

    @Override
    public String toString(){
        return "{" +
                "uid ='" + uid +'\'' +
                ", operationUid ='" + operationUid +'\'' +
                ", systemName ='" + systemName +'\'' +
                ", systemTime ='" + systemTime +'\'' +
                ", source ='" + source +'\'' +
                ", communicationId ='" + communicationId +'\'' +
                ", templatedId ='" + templateId +'\'' +
                ", productCode ='" + productCode +'\'' +
                ", smsCode ='" + smsCode +'\'' +
                '}';
    }
}


