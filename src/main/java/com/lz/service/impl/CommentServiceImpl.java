package com.lz.service.impl;


import com.lz.entity.Comment;
import com.lz.mapper.CommentMapper;
import com.lz.service.CommentService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * 评论操作接口
 *
 * @author jc
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {
        //通过父评论查询评论
//        List<Comment> topComments = commentMapper.getCommentByParentCommentIdAndBlogId((long) -1, blogId);
//        //通过blogId查询所有评论
//        List<Comment> comments = commentMapper.getCommentByBlogId(blogId);
//        //key：顶级评论的id，value：评论集合
//        Map<Long, List<Comment>> topCommentIdMap = new HashMap<>();
//        comments.forEach(comment -> {
//            //获取顶级评论的id
//            Long topCommentId = comment.getTopCommentId();
//            //先判断map中是否有该顶级评论的id，如果有将对应的评论集合赋给 List<Comment>，如果没有新建一个ArrayList给 List<Comment>
//            List<Comment> topCommentList = topCommentIdMap.containsKey(topCommentId) ? topCommentIdMap.get(topCommentId) : new ArrayList<>();
//            //将评论comment添加到topCommentList
//            topCommentList.add(comment);
//            //将顶级评论的id和对应的评论集合放入map中
//            topCommentIdMap.put(topCommentId, topCommentList);
//        });
//        //把topCommentList中每个顶级评论的评论设置为每个评论的子评论
//        topComments.forEach(topComment -> topComment.setChildComments(topCommentIdMap.get(topComment.getId())));

        Long topSize = redisTemplate.opsForList().size(String.valueOf(blogId));
        List<Comment> topComments = redisTemplate.opsForList()
                .range(String.valueOf(blogId), 0, topSize);

        topComments.forEach(topComment -> {
            Long size = redisTemplate.opsForList().size(String.valueOf(topComment.getId()));
            List<Comment> comments = redisTemplate.opsForList()
                    .range(String.valueOf(topComment.getId()), 0, size);
            topComment.setChildComments(comments);
        });
        return topComments;
    }


    /**
     * 注解式事务
     * 例如：
     * @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
     *
     * <p>
     * 事务隔离级别：Isolation，一般用mysql默认，所以一般不需要设置
     * DEFAULT(-1), 数据库默认repeatable
     * READ_UNCOMMITTED(1),读未提交
     * READ_COMMITTED(2),读已提交
     * REPEATABLE_READ(4),可重复度
     * SERIALIZABLE(8);序列化
     * <p>
     * 事务传播特性：Propagation，默认Required，所以一般也不设置
     * REQUIRED(0),如果当前没有事务，就新建一个事务，如果已存在一个事务中，加入到这个事务中，这是最常见的选择。
     * SUPPORTS(1),支持当前事务，如果没有当前事务，就以非事务方法执行。
     * MANDATORY(2),使用当前事务，如果没有当前事务，就抛出异常。
     * REQUIRES_NEW(3),新建事务，如果当前存在事务，把当前事务挂起。
     * NOT_SUPPORTED(4),以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
     * NEVER(5),以非事务方式执行操作，如果当前事务存在则抛出异常。
     * NESTED(6);如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与propagation_required类似的操作
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(Comment comment) {
        //获取父亲评论
        Long parentCommentId = comment.getParentCommentId();
        if (parentCommentId != null && parentCommentId == -1) {
            comment.setParentCommentId(parentCommentId);
            comment.setTopCommentId((long) -1);
        } else {
            //查询上一级评论或回复的情况
            Comment comment1 = commentMapper.getCommentByIdAndBlogId(parentCommentId, comment.getBlogId());
            if (comment1.getTopCommentId() == -1) {
                //表明它的上一级是最高级评论
                comment.setTopCommentId(comment1.getId());
            } else {
                comment.setTopCommentId(comment1.getTopCommentId());
            }
            comment.setParentCommentId(comment1.getId());
        }

        comment.setCreateTime(new Date());
        commentMapper.saveComment(comment);

        // 将评论列表加入redis
        // 没有父级评论
        if (parentCommentId != null && parentCommentId == -1) {
            redisTemplate.opsForList().rightPush(String.valueOf(comment.getBlogId()), comment);
        }
        // 有父级评论
        else {
            // 父级评论id
            Long topCommentId = comment.getTopCommentId();
            redisTemplate.opsForList().rightPush(String.valueOf(topCommentId), comment);
        }
    }

}
