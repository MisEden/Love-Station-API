package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class Auth {
    @Size(min = 5, max = 15)
    private String account;
    @Size(min = 8, max = 15)
    private String password;
}
