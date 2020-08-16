package com.lz.controller.admin;

import com.lz.entity.User;
import com.lz.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class UserController {


    @Resource
    private UserService userService;

    /**
     * 跳转到登录页面，不写value默认是类上面的地址
     *
     * @return
     */
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }


    /**
     * 用户登录操作
     *
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.login(username, password);
        if (user != null) {
            //为了安全，session中不保存密码
            user.setPassword(null);
            session.setAttribute("user", user);
            // 返回后台首页
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }


    /**
     * 用户退出登录
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
