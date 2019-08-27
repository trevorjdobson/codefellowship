package com.trevorjdobson.codefellowship.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome(Principal p, Model m){
        m.addAttribute("user", p);
        return "home";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

}
