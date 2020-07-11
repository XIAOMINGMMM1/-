package cn.wtu.sj.entity;

import lombok.Data;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/16 14:26
 */
@Data
public class UserInfo {
    private Integer userId;
    private String headPortrait;
    private String nickname;
    private String phone;
    private String email;
    private String intro;
    private Integer attention;
    private Integer fans;

}
