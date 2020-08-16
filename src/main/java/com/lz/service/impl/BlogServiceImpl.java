package com.lz.service.impl;

import com.lz.NotFoundException;
import com.lz.entity.Blog;
import com.lz.entity.Tag;
import com.lz.entity.Type;
import com.lz.mapper.BlogMapper;
import com.lz.service.BlogService;
import com.lz.service.TagService;
import com.lz.util.MarkdownUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 博客业务操作实现类
 *
 * @author jc
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    /**
     * @Autowired：ByType
     * @Qualifier：指定name
     * @Resource：ByName 一般开发过程中，用Resource
     */
    @Resource
    private TagService tagService;

    @Override
    public List<Blog> getAllBlog() {
        return blogMapper.getAllBlog();
    }

    @Override
    public void saveBlog(Blog blog) {
        //这是新增
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            blogMapper.saveBlog(blog);
        }
        //更新的话 只要更新就行
        else {
            blog.setUpdateTime(new Date());
        }

    }

    @Override
    public Blog getBlogById(Long id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }

    @Override
    public void updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        blogMapper.updateBlog(blog);
    }

    @Override
    public List<Blog> searchBlog(Blog blog) {
        return blogMapper.searchBlog(blog);
    }


    @Override
    public Integer countBlog() {
        return blogMapper.countBlog();
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogMapper.findByYear(year));
        }
        return map;
    }

    @Override
    public List<Blog> getBlogByTypeId(Long typeId) {
        return blogMapper.getBlogByTypeId(typeId);
    }

    @Override
    public Map<Long, List<Blog>> getBlogByTypes(List<Type> types) {
        List<Blog> blogs = blogMapper.getBlogByTypes(types);
        Map<Long, List<Blog>> typeIdBlogsMap = new HashMap<>();
        blogs.forEach(blog -> {
            Long typeId = blog.getTypeId();
            List<Blog> _blogs = typeIdBlogsMap.containsKey(typeId) ? typeIdBlogsMap.get(typeId) : new ArrayList<>();
            _blogs.add(blog);
            typeIdBlogsMap.put(typeId,_blogs);
        });
        return typeIdBlogsMap;
    }

    @Override
    public List<Blog> getBlogByTagId(Long tagId) {
        return blogMapper.getBlogByTagId(tagId);
    }

    @Override
    public List<Blog> getIndexBlog() {
        return blogMapper.getIndexBlog();
    }

    @Override
    public List<Blog> getRecommendBlog() {
        return blogMapper.getRecommendBlog();
    }

    @Override
    public List<Blog> searchIndexBlog(String query) {
        return blogMapper.searchIndexBlog(query);
    }

    @Override
    public Blog getDetailedBlog(Long id) {
        Blog blog = blogMapper.getDetailedBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }

        // list定义需要指定类型
        List<Tag> tags = new ArrayList();
        String tagIds = blog.getTagIds();
        // 判断为空，不需要判断是否等于空，直接使用equals，并需要将空放在前面，否则如果tagIds为null的话回报NPE
        // 正确方式："".equals(tagIds)
        // 这可以直接使用spring工具包里的StringUtils方法
        if (!StringUtils.isEmpty(tagIds)) {
            String[] tagIdsArray = tagIds.split(",");
            //先获取所有要查询的所有tagId
            List<Long> tagIdList = new ArrayList<>();
            for (String tagId : tagIdsArray) {
                tagIdList.add(Long.parseLong(tagId));
            }
            // 列出所有的tagId，统一查出所有tag对象
            List<Tag> tagList = tagService.getTagsByIds(tagIdList);
            if (!CollectionUtils.isEmpty(tagList)) {
                tags.addAll(tagList);
            }
            blog.setTags(tags);
        }

        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogMapper.updateViews(id);
        return blog;
    }


}
