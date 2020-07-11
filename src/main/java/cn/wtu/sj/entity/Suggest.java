package cn.wtu.sj.entity;

import lombok.Data;

import java.util.Date;

/**
 * 意见表
 */
@Data
public class Suggest {
    private Integer suggestId;
    private Integer userId;
    private String content;
    private Date createTime;
    private String reply;
    private String status;

    //非数据库字段
    private String userName;
    private String realName;
    private String phone;
    private String email;
}
