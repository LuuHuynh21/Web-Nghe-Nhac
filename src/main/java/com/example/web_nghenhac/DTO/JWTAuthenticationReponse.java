package com.example.web_nghenhac.DTO;

import lombok.Data;

@Data
public class JWTAuthenticationReponse {

    private String token;

    private String refeshToken;
}
