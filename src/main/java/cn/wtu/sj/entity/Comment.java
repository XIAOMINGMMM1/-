package cn.wtu.sj.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 18:46
 */
@Data
public class Comment {
    private Integer commentId;
    private Integer essayId;
    private String content;
    private Integer userId;
    private String userName;
    private Date createDate;
}
