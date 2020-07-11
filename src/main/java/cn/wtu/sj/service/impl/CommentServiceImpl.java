package cn.wtu.sj.service.impl;

import cn.wtu.sj.entity.Comment;
import cn.wtu.sj.entity.User;
import cn.wtu.sj.mapper.CommentMapper;
import cn.wtu.sj.service.CommentService;
import cn.wtu.sj.utils.MyUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 19:57
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageInfo<Comment> selectByEssayId(Integer essayId,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectByEssayId(essayId);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        commentPageInfo.setTotal(commentMapper.selectByEssayId(essayId).size());
        return commentPageInfo;
    }

    @Override
    public Comment saveOne(Comment comment) {
        User user = MyUtils.checkLogin();
        Comment c = new Comment();
        c.setContent(comment.getContent());
        c.setCreateDate(new Date());
        c.setUserId(user.getId());
        c.setEssayId(comment.getEssayId());
        commentMapper.insertOne(c);
        return c;
    }

    @Override
    public Comment delete(Integer commentId) {
        User user = MyUtils.checkLogin();
        Comment comment = commentMapper.selectByCommentId(commentId);
        if (user.getUserName().equals(comment.getUserName())){
            commentMapper.deleteByCommentId(comment.getCommentId());
        }
        return comment;
    }


}
