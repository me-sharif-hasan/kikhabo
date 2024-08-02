package com.iishanto.kikhabo.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuccessResponse {
    public SuccessResponse(String status,String message){
        this.status = status;
        this.message = message;
    }
    private String status="success";
    private String message;
    private Object data;
}
