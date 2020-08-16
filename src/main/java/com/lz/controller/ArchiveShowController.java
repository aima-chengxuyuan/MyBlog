package com.lz.controller;

import com.lz.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;


/**
 * @author lize
 */
@Controller
public class ArchiveShowController {

    @Resource
    private BlogService blogService;

    /**
     * 跳转归档页面
     *
     * @param model
     * @return
     */
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
