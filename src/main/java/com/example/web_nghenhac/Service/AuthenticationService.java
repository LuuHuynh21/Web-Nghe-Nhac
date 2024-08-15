package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.DTO.JWTAuthenticationReponse;
import com.example.web_nghenhac.DTO.LoginDTO;
import com.example.web_nghenhac.DTO.RefeshTokenDTO;
import com.example.web_nghenhac.DTO.SignUpDTO;
import com.example.web_nghenhac.entity.NguoiDung;

public interface AuthenticationService {

    NguoiDung signup(SignUpDTO signUpDTO);

    JWTAuthenticationReponse login(LoginDTO loginDTO);

    JWTAuthenticationReponse refeshToken(RefeshTokenDTO refeshTokenDTO);
}
