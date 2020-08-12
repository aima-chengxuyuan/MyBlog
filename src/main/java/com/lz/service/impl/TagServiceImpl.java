package com.lz.service.impl;

import com.lz.entity.Tag;
import com.lz.mapper.TagMapper;
import com.lz.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Transactional//增删改查放在事务里
    @Override
    public List<Tag> getAllTag() {
        return tagMapper.getAllTag();
    }

    @Transactional
    @Override
    public Tag getTagById(Long id) {
        return tagMapper.getTagById(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Transactional
    @Override
    public void saveTag(Tag tag) {
        tagMapper.saveTag(tag);
    }

    @Transactional
    @Override
    public void updateTag(Tag tag) {
        tagMapper.updateTag(tag);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteTag(id);
    }

    @Override
    public List<Tag> getTagByString(String text) {
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(text);
        for (Long long1 : longs) {
            tags.add(tagMapper.getTagById(long1));
        }
        return tags;
    }
    private List<Long> convertToList(String ids) {  //把前端的tagIds字符串1,2,3转换为list集合
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
