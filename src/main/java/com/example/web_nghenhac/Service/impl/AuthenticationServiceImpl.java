package com.example.web_nghenhac.Service.impl;

import com.example.web_nghenhac.DTO.JWTAuthenticationReponse;
import com.example.web_nghenhac.DTO.LoginDTO;
import com.example.web_nghenhac.DTO.RefeshTokenDTO;
import com.example.web_nghenhac.DTO.SignUpDTO;
import com.example.web_nghenhac.Service.AuthenticationService;
import com.example.web_nghenhac.Service.JWTService;
import com.example.web_nghenhac.entity.NguoiDung;
import com.example.web_nghenhac.entity.VaiTro;
import com.example.web_nghenhac.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final NguoiDungRepository nguoiDungRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public NguoiDung signup(SignUpDTO signUpDTO){
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTen(signUpDTO.getTen());
        nguoiDung.setEmail(signUpDTO.getEmail());
        nguoiDung.setMatKhau(passwordEncoder.encode(signUpDTO.getMatKhau()));
        nguoiDung.setVaiTro(VaiTro.USER);

        return nguoiDungRepository.save(nguoiDung);
    }

    public JWTAuthenticationReponse login(LoginDTO loginDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getMatKhau()));
        var nguoiDung = nguoiDungRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
        var jwt = jwtService.generateToken(nguoiDung);
        var refeshToken = jwtService.genarateRefeshToken(new HashMap<>(),nguoiDung);
        JWTAuthenticationReponse jwtAuthenticationReponse = new JWTAuthenticationReponse();

        jwtAuthenticationReponse.setToken(jwt);
        jwtAuthenticationReponse.setRefeshToken(refeshToken);

        return jwtAuthenticationReponse;
    }

    public JWTAuthenticationReponse refeshToken(RefeshTokenDTO refeshTokenDTO){
        String userEmail = jwtService.ExtractUsername(refeshTokenDTO.getToken());
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refeshTokenDTO.getToken(),nguoiDung)){
            var jwt = jwtService.generateToken(nguoiDung);
            JWTAuthenticationReponse jwtAuthenticationReponse = new JWTAuthenticationReponse();

            jwtAuthenticationReponse.setToken(jwt);
            jwtAuthenticationReponse.setRefeshToken(refeshTokenDTO.getToken());

            return jwtAuthenticationReponse;
        }
        return null;
    }
}

