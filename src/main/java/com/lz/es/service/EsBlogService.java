package com.lz.es.service;

import com.lz.entity.Blog;
import com.lz.es.document.EsBlog;

import java.util.List;
import java.util.Map;

/**
 * es中博客操作接口
 *
 * @author wayne
 */
public interface EsBlogService {

    /**
     * 保存博客到es
     *
     * @param esBlog
     */
    void saveBlog(EsBlog esBlog);

    /**
     * 更新博客
     *
     * @param id
     * @param blog
     */
    void updateBlog(Integer id, Blog blog);

    /**
     * 通过博客id获取博客
     *
     * @param id
     * @return
     */
    EsBlog queryBlogById(Integer id);

    /**
     * 查询博客
     *
     * @param keyWords
     * @return
     */
    List<EsBlog> queryBlogs(Map<String, Object> keyWords);


    /**
     * 删除博客,暂时使用物理删除
     *
     * @param blogId
     */
    void deleteBlogs(Integer blogId);


}
