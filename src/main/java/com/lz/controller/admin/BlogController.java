package com.lz.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lz.entity.Blog;
import com.lz.entity.User;
import com.lz.service.BlogService;
import com.lz.service.TagService;
import com.lz.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    /**
     * 定义一个公共方法，获取分类和标签
     *
     * @param model
     */
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
    }


    /**
     * 博客页面
     *
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, Model model) {
        //获取分类列表
        model.addAttribute("types", typeService.getAllType());
        //获取博客列表
        PageHelper.startPage(pageNum, 10);
        List<Blog> blogs = blogService.getAllBlog();
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs";
    }

    /**
     * 后台按条件搜索博客列表
     *
     * @param pageNum
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, Blog blog, Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Blog> blogs = blogService.searchBlog(blog);
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo<>(blogs);
        setTypeAndTag(model);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "查询成功");

        return "admin/blogs";
    }

    //跳转到博客新增页面，同时将分类和标签显示出来
    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());//先初始个blog
        return "admin/blogs-input";
    }

    //新增,编辑博客
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        //设置用户编号
        blog.setUserId(((User) session.getAttribute("user")).getId());

        //设置分类编号
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        if (blog.getId() == null) {
            //新增
            blogService.saveBlog(blog);
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            //更新
            blogService.updateBlog(blog);
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 跳转到更新页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlogById(id);
        blog.setType(typeService.getTypeById(blog.getTypeId()));
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }


    /**
     * 删除博客
     *
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }


}
