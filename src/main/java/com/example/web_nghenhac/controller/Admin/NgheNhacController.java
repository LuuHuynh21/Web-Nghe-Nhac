package com.example.web_nghenhac.controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class NgheNhacController {

    @GetMapping("/index")
    public String hienThi(){
        return "index";
    }

}
