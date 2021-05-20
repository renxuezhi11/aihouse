package com.aihouse.aihouseweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){ return "redirect:/dowload"; }

    @RequestMapping("/dowload")
    public String todowload(){ return "index"; }

    @RequestMapping("/privacypolicy")
    public String privacy_policy(){
        return "privacy_policy";
    }

}
