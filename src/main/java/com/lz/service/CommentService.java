package com.lz.service;

import com.lz.entity.Comment;

import java.util.List;

public interface CommentService {
    void saveComment(Comment comment);
    List<Comment> getCommentByBlogId(Long blogId);
}
