package com.jeremy.advsearchemployee.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIresponse {

    private Object data;
    private String message;
    private HttpStatus reponseCode;
}
