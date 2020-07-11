package cn.wtu.sj.entity;

import lombok.Data;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 12:58
 */
@Data
public class User {
    private Integer id;
    private String userName;
    private String passWord;
    private String realName;
    private String enable;
    private String roles;
//
//    private String nickName;
//    private String email;
//    private String phone;
//    private String intro;
   private String oldPassWord;


    private Integer userId;
    private String headPortrait;
    private String nickname;
    private String phone;
    private String email;
    private String intro;
    private Integer attention;
    private Integer fans;
}
