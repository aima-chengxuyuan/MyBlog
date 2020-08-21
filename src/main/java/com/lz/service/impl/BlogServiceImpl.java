package com.lz.service.impl;

import com.lz.NotFoundException;
import com.lz.entity.Blog;
import com.lz.entity.Tag;
import com.lz.entity.Type;
import com.lz.es.document.EsBlog;
import com.lz.es.service.EsBlogService;
import com.lz.mapper.BlogMapper;
import com.lz.service.BlogService;
import com.lz.service.TagService;
import com.lz.util.MarkdownUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private EsBlogService esBlogService;
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
        Date nowDate = new Date();
        Long blogId = blog.getId();
        if (blogId == null) {
            blog.setCreateTime(nowDate);
            blog.setUpdateTime(nowDate);
            blog.setViews(0);
            blogMapper.saveBlog(blog);
            log.info("新增blogId:{}", blog.getId());
            // 新增blog记录到es
            EsBlog esBlog = new EsBlog();
            esBlog.setId(Integer.parseInt(blog.getId().toString()));
            esBlog.setTitle(blog.getTitle());
            esBlog.setContent(blog.getContent());
            esBlog.setUpdateTime(nowDate);
            esBlog.setIsDeleted(0);
            esBlogService.saveBlog(esBlog);
        }
        //更新的话 只要更新就行
        else {
            esBlogService.updateBlog(Integer.parseInt(blogId.toString()), blog);
            blog.setUpdateTime(nowDate);
        }
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
        esBlogService.deleteBlogs(Integer.parseInt(id.toString()));
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

    //map的key：年份，value：该年份的博客
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
    //用于表现层显示所有的blog，返回一个map，key是所有存在blog的type，value是对应type的blog集合
    public Map<Long, List<Blog>> getBlogByTypes(List<Type> types) {
        //1、获取所有type的所有blog
        List<Blog> blogs = blogMapper.getBlogByTypes(types);
        //2、创建一个map，key：typeid，value：对应的blog集合
        Map<Long, List<Blog>> typeIdBlogsMap = new HashMap<>();
        //3、将所有存在的typeid和对应的blog集合放入map中
        blogs.forEach(blog -> {
            //3.1、获取每个blog的typeid
            Long typeId = blog.getTypeId();
            //3.2、先判断键中是否存在该typeid，如果存在返回对应的value-blog集合，如果不存在新建一个list用来存放对应的blog
            List<Blog> _blogs = typeIdBlogsMap.containsKey(typeId) ? typeIdBlogsMap.get(typeId) : new ArrayList<>();
            //3.3、将此blog添加到blog集合中
            _blogs.add(blog);
            //3.4、将键值对放入map中
            typeIdBlogsMap.put(typeId, _blogs);
        });
        //上面是通过blog得到的type，并将其放入map
        //对于有些没有blog的type，也需要放入map中，value为新建的空的list
        types.forEach(type -> {
            //如果Map的键中不存在该type的typeid
            if (!typeIdBlogsMap.containsKey(type.getId())) {
                typeIdBlogsMap.put(type.getId(), new ArrayList<>());
            }
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
        // 如果查询的是标签或内容,则从es中进行查询
        Map<String, Object> keywordMap = new HashMap<>();
        if (!StringUtils.isEmpty(query)) {
            keywordMap.put("content", query);
            keywordMap.put("title", query);
        }
        List<EsBlog> esBlogs = esBlogService.queryBlogs(keywordMap);
        List<Long> ids = new ArrayList<>();

        esBlogs.forEach(esBlog -> ids.add(Long.parseLong(esBlog.getId().toString())));
        return blogMapper.queryBlogByIds(ids);
//        return blogMapper.searchIndexBlog(query);
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
