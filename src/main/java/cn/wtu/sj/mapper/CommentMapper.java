package cn.wtu.sj.mapper;

import cn.wtu.sj.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 18:47
 */
@Repository
public interface CommentMapper {
    void insertOne(Comment comment);
    void deleteByEssayId(Integer essayId);
    void deleteByCommentId(Integer commentId);
    List<Comment> selectByEssayId(Integer essayId);
    Comment selectByCommentId(Integer commentId);
}
