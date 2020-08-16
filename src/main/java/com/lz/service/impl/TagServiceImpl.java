package com.lz.service.impl;

import com.lz.entity.Tag;
import com.lz.mapper.TagMapper;
import com.lz.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    /**
     * 增删改放在事务里
     * <p>
     * 查不放在事务里
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Tag> getAllTag() {
        return tagMapper.getAllTag();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tag getTagById(Long id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public List<Tag> getTagsByIds(List<Long> tagIdList) {
        return tagMapper.getTagsByIds(tagIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTag(Tag tag) {
        tagMapper.saveTag(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(Tag tag) {
        tagMapper.updateTag(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long id) {
        tagMapper.deleteTag(id);
    }

    @Override
    public List<Tag> getTagByString(String text) {
        List<Long> idList = convertToList(text);
        return tagMapper.getTagsByIds(idList);
    }

    /**
     * 4把前端的tagIds字符串1,2,3转换为list集合
     *
     * @param ids
     * @return
     */
    private List<Long> convertToList(String ids) {
        List<Long> idList = new ArrayList<>();
        if (!StringUtils.isEmpty(ids)) {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                idList.add(Long.parseLong(id));
            }
        }
        return idList;
    }
}
