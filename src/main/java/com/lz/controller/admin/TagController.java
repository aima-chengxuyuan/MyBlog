package com.lz.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lz.entity.Tag;
import com.lz.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 获取分页后的标签
     *
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Tag> tags = tagService.getAllTag();
        PageInfo pageInfo = new PageInfo<>(tags);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/tags";
    }

    /**新增tag
     * 1、新建一个tag，用来传递到前端
     * 2、跳转到tag的编辑页面admin/tags-input.html,并将要添加的tag的数据回显在form表单中，此时tag的中的数据均为null
     * 3、跳转到tag的编辑页面admin/tags-input.html后，通过post方式提交，form中的action为admin/tags)
     * 4、然后会调用Post方法，对tag进行保存
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model) {
        //给新增页面传递一个object对象
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**编辑tag
     * 1、通过id获取tag
     * 2、跳转到tag的编辑页面admin/tags-input.html,并将要修改的tag的数据回显在form表单中
     * 3、在form中填完数据后，通过post方式提交，form中的action为admin/tags/{id}(id=*{id})
     * 4、然后会调用editpost方法，对tag进行保存
     * form中action中
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model) {
        //首先通过编号获取到标签
        Tag tag = tagService.getTagById(id);

        //回显数据
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    /**
     * 新增标签操作
     *
     * @param tag
     * @param attributes
     * @param model
     * @return
     */
    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes, Model model) {
        String name = tag.getName();
        Tag tag1 = tagService.getTagByName(name);
        //不存在这个name的type，代表没有重复
        if (tag1 == null) {
            tagService.saveTag(tag);
            attributes.addFlashAttribute("message", "新增成功");
            //不能直接返回到/admin/tags"，需要经过反复查询，不然拿不到数据
            return "redirect:/admin/tags";
        } else {
            attributes.addFlashAttribute("message", "不能添加重复的类");
            return "redirect:/admin/tags/input";
        }
    }

    /**
     * 编辑标签
     * @param tag
     * @param attributes
     * @param model
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, RedirectAttributes attributes, Model model) {
        String name = tag.getName();
        Tag tag1 = tagService.getTagByName(name);
        //不存在这个name的type，代表没有重复
        if (tag1 == null) {
            tagService.updateTag(tag);
            attributes.addFlashAttribute("message", "编辑成功");
            //不能直接返回到/admin/tags"，需要经过反复查询，不然拿不到数据
            return "redirect:/admin/tags";
        } else {
            attributes.addFlashAttribute("message", "不能添加重复的类");
            return "redirect:/admin/tags/input";
        }
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

}
