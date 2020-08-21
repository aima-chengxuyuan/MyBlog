package com.lz.mapper;

import com.lz.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TagMapper {

    /**
     * 获取所有的tag
     *
     * @return
     */
    List<Tag> getAllTag();

    /**
     * 通过id查找Tag
     *
     * @param id
     * @return
     */
    Tag getTagById(@Param("id") Long id);

    /**
     * 通过多个id查找多个Tag
     *
     * @param ids
     * @return
     */
    List<Tag> getTagsByIds(@Param("tagIdList") List<Long> ids);

    /**
     * 通过name查询Tag
     *
     * @param name
     * @return
     */
    Tag getTagByName(@Param("name") String name);

    /**
     * 保存Tag
     *
     * @param tag
     */
    void saveTag(Tag tag);

    /**
     * 更新Tag
     *
     * @param tag
     */
    void updateTag(Tag tag);

    /**
     * 通过id删除Tag
     *
     * @param id
     */
    void deleteTag(Long id);

//    Tag getTag(@Param("id") Long id);
}
