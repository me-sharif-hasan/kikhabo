package com.iishanto.kikhabo.web.response;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String status;
    private ErrorCodes code;
    private List<String> message;

    public static ErrorResponse of(List<String> message,ErrorCodes code){
        return ErrorResponse.builder().code(code).message(message).status("error").build();
    }
}
