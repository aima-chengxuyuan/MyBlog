package com.lz.mapper;

import com.lz.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TagMapper {
    List<Tag> getAllTag();
    Tag getTagById(@Param("id") Long id);
    Tag getTagByName(@Param("name") String name);
    void saveTag(Tag tag);
    void updateTag(Tag tag);
    void deleteTag(Long id);
//    Tag getTag(@Param("id") Long id);
}
