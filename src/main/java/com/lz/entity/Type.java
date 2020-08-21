package com.lz.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Type {

    /**编号*/
    private Long id;

    /**分类名*/
    private String name;

    /**级联关系*/
    //这个成员属性是用来在标签页中，获取每个Type的blog，数据库中并没有次列名
    //每次对Type的新增或修改并不需要对其添加对应的blogs，
    //所以和Type的级联关系是一对一，且是单向的
    private List<Blog> blogs = new ArrayList<>();

}
