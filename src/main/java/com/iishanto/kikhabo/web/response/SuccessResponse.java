package com.iishanto.kikhabo.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuccessResponse <R> {
    public SuccessResponse(String status,String message){
        this.status = status;
        this.message = message;
    }
    private String status="success";
    private String message;
    private R data;
}
