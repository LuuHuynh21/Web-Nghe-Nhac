package com.example.web_nghenhac.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user-index")
public class HomeController {

    @GetMapping
    public String userHT(){
        return "category/user-index";
    }
}
