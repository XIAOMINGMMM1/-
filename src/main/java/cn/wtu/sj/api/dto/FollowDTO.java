package cn.wtu.sj.api.dto;

import lombok.Data;

@Data
public class FollowDTO {
    private Integer followId;
    private Integer userId;
    private Integer followUserId;
    private String userName;
    private String intro;
}
