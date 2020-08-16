package com.lz.mapper;

import com.lz.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {

    /**
     * 新增评论
     *
     * @param comment
     */
    void saveComment(Comment comment);

    /**
     * 通过编号获取评论
     *
     * @param id
     * @param blogId
     * @return
     */
    Comment getCommentByIdAndBlogId(@Param("id") Long id, @Param("blogId") Long blogId);

    /**
     * 查询评论通过父评论
     * t
     *
     * @param parentCommentId
     * @param blogId
     * @return
     */
    List<Comment> getCommentByParentCommentIdAndBlogId(@Param("parentCommentId") Long parentCommentId, @Param("blogId") Long blogId);

    /**
     * 查询评论通过顶级评论
     *
     * @param topCommentId
     * @param blogId
     * @return
     */
    @Deprecated
    List<Comment> getCommentByTopCommentAndBlogId(@Param("topCommentId") Long topCommentId, @Param("blogId") Long blogId);


    /**
     * 通过blogId查询所有顶级评论
     *
     * @param blogId
     * @return
     */
    List<Comment> getCommentByBlogId(@Param("blogId") Long blogId);
}
