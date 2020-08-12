package com.lz.mapper;

import com.lz.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface BlogMapper {
    List<Blog> getAllBlog();
    void saveBlog(Blog blog);
    Blog getBlogById(@Param("id") Long id);
    void deleteBlog(Long id);
    void updateBlog(Blog blog);
    List<Blog> searchBlog(Blog blog);


    Integer countBlog();
    List<String> findGroupYear();
    List<Blog> findByYear(@Param("year")String year);


    List<Blog> getBlogByTypeId(@Param("typeId")Long typeId);
    List<Blog> getBlogByTagId(@Param("tagId")Long tagId);

    List<Blog> getIndexBlog();
    List<Blog> getRecommendBlog();
    List<Blog> searchIndexBlog(@Param("query")String query);
    Blog getDetailedBlog(@Param("id")Long id);
    void updateViews(@Param("id")Long id);
}
