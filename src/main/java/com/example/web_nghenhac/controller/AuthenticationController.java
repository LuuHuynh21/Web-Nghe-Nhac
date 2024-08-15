package com.example.web_nghenhac.controller;

import com.example.web_nghenhac.DTO.JWTAuthenticationReponse;
import com.example.web_nghenhac.DTO.LoginDTO;
import com.example.web_nghenhac.DTO.RefeshTokenDTO;
import com.example.web_nghenhac.DTO.SignUpDTO;
import com.example.web_nghenhac.Service.AuthenticationService;
import com.example.web_nghenhac.entity.NguoiDung;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<NguoiDung> signup(@RequestBody SignUpDTO signUpDTO){
        return ResponseEntity.ok(authenticationService.signup(signUpDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<JWTAuthenticationReponse> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authenticationService.login(loginDTO));
    }
    @PostMapping("/refesh")
    public ResponseEntity<JWTAuthenticationReponse> refesh(@RequestBody RefeshTokenDTO refeshTokenDTO){
        return ResponseEntity.ok(authenticationService.refeshToken(refeshTokenDTO));
    }
}
