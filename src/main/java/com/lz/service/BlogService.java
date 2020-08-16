package com.lz.service;

import com.lz.entity.Blog;
import com.lz.entity.Type;

import java.util.List;
import java.util.Map;

/**
 * 博客相关操作接口
 *
 * @author jc
 */
public interface BlogService {

    /**
     * 获取所有博客
     *
     * @return
     */
    List<Blog> getAllBlog();

    /**
     * 保存博客
     *
     * @param blog
     */
    void saveBlog(Blog blog);

    /**
     * 通过id获取博客
     *
     * @param id
     * @return
     */
    Blog getBlogById(Long id);

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
     * 搜索博客
     *
     * @param blog
     * @return
     */
    List<Blog> searchBlog(Blog blog);

    /**
     * 统计博客数量
     *
     * @return
     */
    Integer countBlog();

    /**
     * 博客归档
     *
     * @return
     */
    Map<String, List<Blog>> archiveBlog();


    /**
     * 通过类型id查找博客
     *
     * @param typeId
     * @return
     */
    List<Blog> getBlogByTypeId(Long typeId);

    /**
     * 通过多个Type对象中的typeId 查询所有匹配的博客
     * <p>
     * key:typeId
     * value:blog集合
     *
     * @param types
     * @return
     */
    Map<Long, List<Blog>> getBlogByTypes(List<Type> types);

    /**
     * 通过标签id查找博客
     *
     * @param tagId
     * @return
     */
    List<Blog> getBlogByTagId(Long tagId);


    /**
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
     * 搜索博客
     *
     * @param query
     * @return
     */
    List<Blog> searchIndexBlog(String query);

    /**
     * 获取博客详情
     *
     * @param id
     * @return
     */
    Blog getDetailedBlog(Long id);


}
