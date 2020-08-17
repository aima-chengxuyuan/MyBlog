package com.lz.es.document;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * es中的博客文档
 * createIndex = false,
 *
 * @author wayne
 */
@Data
@Document(indexName = "blog", type = "blog", useServerConfiguration = true, refreshInterval = "0s")
public class EsBlog {

    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * 标题
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word")
    private String title;

    /**
     * 内容
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word")
    private String content;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private Date updateTime;

    /**
     * 是否删除,逻辑删除
     */
    private Integer isDeleted;


}
