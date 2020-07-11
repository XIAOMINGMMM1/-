package cn.wtu.sj.entity;

import lombok.Data;

@Data
public class Follow {
    private Integer followId;
    private Integer userId;
    private Integer followUserId;
}
