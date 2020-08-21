package com.lz.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lz.entity.Blog;
import com.lz.entity.Type;
import com.lz.service.BlogService;
import com.lz.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;


@Controller
public class TypeShowController {

    @Resource
    private TypeService typeService;

    @Resource
    private BlogService blogService;

    /**
     * 加载类型及类型下的博客
     *
     * @param pageNum
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @PathVariable("id") Long id, Model model) {
        //获取所有分类
        List<Type> types = typeService.getAllType();

        //博客首页点击more进来的
        if (id == -1) {
            //自动选择第一个分类，显示其下的博客
            id = types.get(0).getId();
        }

        PageHelper.startPage(pageNum, 5);
        //通过typeid获取对应的blogs
        List<Blog> blogs = blogService.getBlogByTypeId(id);
        PageInfo pageInfo = new PageInfo<>(blogs);
        for (Type type : types) {
            type.setBlogs(blogService.getBlogByTypeId(type.getId()));
        }

        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        //用于标签颜色是否点亮
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
