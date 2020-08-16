package com.lz.service;

import com.lz.entity.Tag;

import java.util.List;

public interface TagService {

    /**
     * 获取所有标签
     *
     * @return
     */
    List<Tag> getAllTag();

    /**
     * 通过id获取标签
     *
     * @param id
     * @return
     */
    Tag getTagById(Long id);

    /**
     * 通过多个tagId，统一查出所有tag对象
     *
     * @param tagIdList
     * @return
     */
    List<Tag> getTagsByIds(List<Long> tagIdList);

    /**
     * 通过名称获取标签
     *
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 保存标签
     *
     * @param tag
     */
    void saveTag(Tag tag);

    /**
     * 更新标签
     *
     * @param tag
     */
    void updateTag(Tag tag);

    /**
     * 删除标签
     *
     * @param id
     */
    void deleteTag(Long id);

    /**
     * 从字符串中获取tag集合
     *
     * @param text
     * @return
     */
    List<Tag> getTagByString(String text);

}
