package cn.wtu.sj.service;

import cn.wtu.sj.entity.Comment;
import com.github.pagehelper.PageInfo;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 19:57
 */
public interface CommentService {
    PageInfo<Comment> selectByEssayId(Integer essayId, Integer pageNum, Integer pageSize);

    Comment saveOne(Comment comment);

    Comment delete(Integer commentId);
}
