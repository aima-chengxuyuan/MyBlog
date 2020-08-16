package com.lz.es.repository;

import com.lz.es.document.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * blog的es操作
 *
 * @author wayne
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Long> {




}
