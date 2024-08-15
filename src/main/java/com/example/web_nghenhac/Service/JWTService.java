package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.entity.NguoiDung;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JWTService {
    String generateToken(UserDetails userDetails);

    String ExtractUsername(String token);

    boolean isTokenValid(String token , UserDetails userDetails);

    String genarateRefeshToken(Map<String,Object> extraClaims, UserDetails userDetails);
}
