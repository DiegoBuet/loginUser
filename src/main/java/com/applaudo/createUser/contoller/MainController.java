package com.applaudo.createUser.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/loginIndex")
    public String loginPage() {
        return "loginIndex";
    }
}
