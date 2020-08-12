package com.lz.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lz.entity.Type;
import com.lz.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //获取分页后的分类
    @GetMapping("/types")//Model是thymeleaf的内容，是前端的页面展示模型，并用来传值。这一段是分页处理。
    public String types(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum, Model model) {
        PageHelper.startPage(pageNum,5);
        List<Type> types=typeService.getAllType();
        PageInfo pageInfo=new PageInfo<>(types);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    //跳转到新增页面
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type()); //给新增页面传递一个object对象，返回一个type对象给前端th:object，这里这个Type类里面的id和name是null
        return "admin/types-input";
    }

    //跳转到编辑页面,并将要修改的数据回显
    @GetMapping("/types/{id}/input") //这里是必须要带id的，没id没法查询啊。
    public String editInput(@PathVariable("id") Long id, Model model) {
        //首先通过编号获取到分类
        Type type=typeService.getTypeById(id);

        //回显数据，不回显的话，拿不到原来的type，修改页面就不会显示原来type中的数据
        model.addAttribute("type",type);
        return "admin/types-input";
    }

    //新增分类操作
    @PostMapping("/types")//没带id的是要添加的
    public String post(Type type, RedirectAttributes attributes, Model model) {
        String name = type.getName();
        Type type1 = typeService.getTypeByName(name);
        if(type1==null){//不存在这个name的type，代表没有重复
            typeService.saveType(type);
            attributes.addFlashAttribute("message", "新增成功");
            return "redirect:/admin/types";//不能直接返回到/admin/types"，需要经过反复查询，不然拿不到数据
        }else {
            attributes.addFlashAttribute("message", "不能添加重复的类");
            return "redirect:/admin/types/input";
        }

    }


    //编辑分类
    @PostMapping("/types/{id}")//带id的是要更新的
    public String editPost(Type type, RedirectAttributes attributes,Model model) {
        String name = type.getName();
        Type type1 = typeService.getTypeByName(name);
        if(type1==null){//不存在这个name的type，代表没有重复
            typeService.updateType(type);
            attributes.addFlashAttribute("message", "编辑成功");
            return "redirect:/admin/types";//不能直接返回到/admin/types"，需要经过反复查询，不然拿不到数据
        }else {
            attributes.addFlashAttribute("message", "不能添加重复的类");
            return "redirect:/admin/types/input";
        }
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
