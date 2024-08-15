package com.example.web_nghenhac;

import com.example.web_nghenhac.Service.NguoiDungService;
import com.example.web_nghenhac.entity.NguoiDung;
import com.example.web_nghenhac.entity.VaiTro;
import com.example.web_nghenhac.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebNgheNhacApplication implements CommandLineRunner {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebNgheNhacApplication.class, args);
    }

    public void run(String... args){
        NguoiDung adminAccount = nguoiDungRepository.findByVaiTro(VaiTro.USER);
        if(null == adminAccount){
            NguoiDung nguoiDung = new NguoiDung();

            nguoiDung.setEmail("user@gmail.com");
            nguoiDung.setTen("user");
            nguoiDung.setVaiTro(VaiTro.USER);
            nguoiDung.setMatKhau(new BCryptPasswordEncoder().encode("user"));
            nguoiDungRepository.save(nguoiDung);
        }
    }
}
