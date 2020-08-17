package com.lz.es.service.impl;

import com.lz.entity.Blog;
import com.lz.es.document.EsBlog;
import com.lz.es.repository.EsBlogRepository;
import com.lz.es.service.EsBlogService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * es中博客操作实现类
 *
 * @Author wayne
 * @Date 2020/8/17 11:54
 */
@Service
@Slf4j
public class EsBlogServiceImpl implements EsBlogService {

    @Resource
    private EsBlogRepository esBlogRepository;

    @Override
    public void saveBlog(EsBlog esBlog) {
        Date date = new Date();
        esBlog.setUpdateTime(date);
        esBlogRepository.save(esBlog);
    }

    @Override
    public void updateBlog(Integer id, Blog blog) {
        EsBlog esBlog = queryBlogById(id);
        if (esBlog != null) {
            esBlog.setTitle(blog.getTitle());
            esBlog.setContent(blog.getContent());
            esBlogRepository.save(esBlog);
            saveBlog(esBlog);
        }
    }

    @Override
    public EsBlog queryBlogById(Integer id) {
        Optional<EsBlog> esBlogOptional = esBlogRepository.findById(id);
        return esBlogOptional.get();
    }

    @Override
    public List<EsBlog> queryBlogs(Map<String, Object> keyWords) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // should 相当于 or
        for (Map.Entry<String, Object> entry : keyWords.entrySet()) {
            builder.should(QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue()));
        }
        log.info("查询条件:{}", builder.toString());
        Page<EsBlog> pages = (Page<EsBlog>) esBlogRepository.search(builder);
        return pages.getContent();
    }

    @Override
    public void deleteBlogs(Integer blogId) {
        esBlogRepository.deleteById(blogId);
    }
}
