package com.lz.controller;

import com.lz.entity.Comment;
import com.lz.entity.User;
import com.lz.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Resource
    private CommentService commentService;


    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 通过博客id查询评论
     *
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model) {
        List<Comment> comments = commentService.getCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }


    /**
     * 评论
     *
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        //获取博客编号
        Long blogId = comment.getBlog().getId();
        comment.setBlogId(blogId);

        //获取被回复的昵称
        String replyName = comment.getReplyName();
        if (replyName.equals("")) {
            comment.setReplyName(null);
        }

        //判断用户是否登录
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //登录，则用用户设置的头像
            comment.setAvatar(user.getAvatar());
            //是管理员评论
            comment.setAdminComment(true);
        } else {
            //是游客，使用默认的头像
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }


}
