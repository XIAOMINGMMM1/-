package cn.wtu.sj.api.controller;

import cn.wtu.sj.entity.Comment;
import cn.wtu.sj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 20:21
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping("/ordinary/comment/save-one")
    public ResponseEntity<Comment> submitComment(@RequestBody Comment comment){
        comment = commentService.saveOne(comment);
        return ResponseEntity.ok(comment);
    }

    @RequestMapping("/ordinary/comment/delete")
    public ResponseEntity<Comment> delete(Integer commentId){
        Comment comment = commentService.delete(commentId);
        return ResponseEntity.ok(comment);
    }
}
