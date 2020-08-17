package com.lz.mapper;

import com.lz.entity.Blog;
import com.lz.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 博客mapper
 *
 * @author jc
 */
@Component
@Mapper
public interface BlogMapper {
    /**
     * 后台获取博客
     *
     * @return
     */
    List<Blog> getAllBlog();

    /**
     * 新增博客
     *
     * @param blog
     * @return
     */
    void saveBlog(Blog blog);

    /**
     * 通过编号获取博客
     *
     * @param id
     * @return
     */
    Blog getBlogById(@Param("id") Long id);

    /**
     * 删除博客
     *
     * @param id
     */
    void deleteBlog(Long id);

    /**
     * 更新博客
     *
     * @param blog
     */
    void updateBlog(Blog blog);

    /**
     * 后台搜索博客
     *
     * @param blog
     * @return
     */
    List<Blog> searchBlog(Blog blog);

    /**
     * 查询博客数量
     *
     * @return
     */
    Integer countBlog();

    /**
     * 查询年份集合
     *
     * @return
     */
    List<String> findGroupYear();

    /**
     * 通过年份获得博客
     *
     * @param year
     * @return
     */
    List<Blog> findByYear(@Param("year") String year);

    /**
     * 通过分类编号获取博客列表
     *
     * @param typeId
     * @return
     */
    List<Blog> getBlogByTypeId(@Param("typeId") Long typeId);

    /**
     * 通过多个分类编号获取博客列表
     *
     * @param types
     * @return
     */
    List<Blog> getBlogByTypes(@Param("types") List<Type> types);

    /**
     * 通过标签编号获取博客列表
     *
     * @param tagId
     * @return
     */
    List<Blog> getBlogByTagId(@Param("tagId") Long tagId);

    /**
     * 前台获取博客信息
     *
     * @return
     */
    List<Blog> getIndexBlog();

    /**
     * 获得最新推荐的博客
     *
     * @return
     */
    List<Blog> getRecommendBlog();

    /**
     * 前端全局搜索
     *
     * @param query
     * @return
     */
    List<Blog> searchIndexBlog(@Param("query") String query);

    /**
     * 通过博客id获取博客
     *
     * @param ids
     * @return
     */
    List<Blog> queryBlogByIds(@Param("ids") List<Long> ids);

    /**
     * 获取博客详情
     *
     * @param id
     * @return
     */
    Blog getDetailedBlog(@Param("id") Long id);

    /**
     * 更新浏览数
     *
     * @param id
     */
    void updateViews(@Param("id") Long id);
}
