package com.lz.service;

import com.lz.entity.Comment;

import java.util.List;

/**
 * 评论操作接口
 *
 * @author jc
 */
public interface CommentService {

    /**
     * 保存评论
     *
     * @param comment
     */
    void saveComment(Comment comment);

    /**
     * 通过博客id获取评论
     *
     * @param blogId
     * @return
     */
    List<Comment> getCommentByBlogId(Long blogId);
}
