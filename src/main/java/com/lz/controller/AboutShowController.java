package com.lz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AboutShowController {

    /**
     * 跳转关于页面
     *
     * @return
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
