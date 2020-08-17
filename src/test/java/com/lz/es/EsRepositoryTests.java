package com.lz.es;


import com.lz.es.document.EsBlog;
import com.lz.es.repository.EsBlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class EsRepositoryTests {

    @Resource
    private EsBlogRepository esBlogRepository;

    @Test
    public void testEs() {
        long count = esBlogRepository.count();
        Optional<EsBlog> test = esBlogRepository.findById(123);


        Iterable<EsBlog> all = esBlogRepository.findAll();
        Iterator<EsBlog> esBlogIterator = all.iterator();
        for (EsBlog esBlog : all) {
            System.out.println(esBlog.toString());
        }
        EsBlog esBlog = esBlogIterator.next();
        System.out.println("esBlog = " + esBlog);
    }

    /**
     * 测试保存
     */
    @Test
    public void testSave() {
        EsBlog esBlog = new EsBlog();
//        esBLog.setId(2);
//        esBLog.setContent("测试elastic search content");
//        esBLog.setTitle("测试elastic search title");
//        Date date = new Date();
//        esBLog.setUpdateTime(date);

        esBlog.setId(4);
        esBlog.setContent("这是测试内容");
        esBlog.setTitle("这是测试标题");
        esBlog.setIsDeleted(0);
        Date date = new Date();
        esBlog.setUpdateTime(date);

        esBlogRepository.save(esBlog);
    }

    /**
     * 测试搜索
     */
    @Test
    public void testSearch() {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // should 相当于 or
        builder.should(QueryBuilders.matchPhraseQuery("title", "测试"));
        builder.should(QueryBuilders.matchPhraseQuery("content", "内容"));
        builder.must(QueryBuilders.matchPhraseQuery("isDeleted", 0));
        log.info("查询条件:{}", builder.toString());
        Page<EsBlog> pages = (Page<EsBlog>) esBlogRepository.search(builder);
        List<EsBlog> content = pages.getContent();
        log.info("查询结果：{}", content.toString());
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        // mysql中的博客id
        Integer blogId = 2;
        Optional<EsBlog> esBlogOptional = esBlogRepository.findById(blogId);
        if (esBlogOptional.isPresent()) {
            EsBlog esBlog = esBlogOptional.get();
//            esBlog.setContent(esBlog.getContent() + " 啦啦啦啦");
//            esBlog.setTitle(esBlog.getTitle() + " 啦啦");
            esBlog.setIsDeleted(0);
            esBlogRepository.save(esBlog);
        }
    }

    /**
     * 测试删除，逻辑删除
     */
    @Test
    public void testDelete() {
        Integer blogId = 4;
        //物理删除
        esBlogRepository.deleteById(blogId);
        Optional<EsBlog> esBlogOptional = esBlogRepository.findById(blogId);
        Assert.assertFalse(esBlogOptional.isPresent());
    }


}
