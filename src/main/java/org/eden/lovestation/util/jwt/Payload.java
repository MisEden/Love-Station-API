package org.eden.lovestation.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payload {
    private String userId;
    private String role;
}
