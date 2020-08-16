package com.lz.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lz.entity.Blog;
import com.lz.entity.Tag;
import com.lz.entity.Type;
import com.lz.service.BlogService;
import com.lz.service.TagService;
import com.lz.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lize
 */
@Controller
public class IndexController {

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    /**
     * 首页信息
     *
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 6);
        //获取所有分类
        List<Type> types = typeService.getAllType();
        if (!CollectionUtils.isEmpty(types)) {
            Map<Long, List<Blog>> typeIdBlogsMap = blogService.getBlogByTypes(types);
            types.forEach(type -> type.setBlogs(typeIdBlogsMap.get(type.getId())));
        }
        PageHelper.startPage(pageNum, 6);
        //获取所有标签
        List<Tag> tags = tagService.getAllTag();
        for (Tag tag : tags) {
            tag.setBlogs(blogService.getBlogByTagId(tag.getId()));
        }

        PageHelper.startPage(pageNum, 6);
        //前台获取博客
        List<Blog> blogs = blogService.getIndexBlog();
        PageInfo pageInfo = new PageInfo<>(blogs);

        PageHelper.startPage(pageNum, 6);
        //前台获取推荐博客
        List<Blog> recommendBlogs = blogService.getRecommendBlog();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        model.addAttribute("recommendBlogs", recommendBlogs);
        return "index";
    }


    /**
     * 搜索博客
     *
     * @param pageNum
     * @param query
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                         @RequestParam String query, Model model) {
        PageHelper.startPage(pageNum, 6);
        List<Blog> blogs = blogService.searchIndexBlog(query);
        PageInfo pageInfo = new PageInfo<>(blogs);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    /**
     * 通过id查询博客详细内容
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);
        return "blog";
    }

    /**
     * 获得最新推荐的博客
     *
     * @param model
     * @return
     */
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        PageHelper.startPage(1, 2);
        //前台获取推荐博客
        List<Blog> recommendBlogs = blogService.getRecommendBlog();
        model.addAttribute("newblogs", recommendBlogs);
        return "_fragments :: newblogList";
    }

}
