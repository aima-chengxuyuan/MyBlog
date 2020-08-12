package com.lz.service;

import com.lz.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTag();
    Tag getTagById(Long id);
    Tag getTagByName(String name);
    void saveTag(Tag tag);
    void updateTag(Tag tag);
    void deleteTag(Long id);
    List<Tag> getTagByString(String text);   //从字符串中获取tag集合

}
