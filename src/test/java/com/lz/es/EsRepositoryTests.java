package com.lz.es;


import com.lz.es.document.EsBlog;
import com.lz.es.repository.EsBlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Iterator;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class EsRepositoryTests {

    @Resource
    private EsBlogRepository esBlogRepository;

    @Test
    public void testEs() {
        Iterable<EsBlog> all = esBlogRepository.findAll();
        Iterator<EsBlog> esBlogIterator = all.iterator();
    }

    /**
     * 测试保存
     */
    @Test
    public void testSave() {

    }

    /**
     * 测试搜索
     */
    public void testSearch() {

    }

    /**
     * 测试更新
     */
    public void testUpdate() {

    }

    /**
     * 测试删除，逻辑删除
     */
    public void testDelete() {

    }


}
