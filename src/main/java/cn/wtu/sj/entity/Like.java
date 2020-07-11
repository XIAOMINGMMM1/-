package cn.wtu.sj.entity;

import lombok.Data;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 15:42
 */
@Data
public class Like {
    private Integer likeId;
    private Integer userId;
    private Integer essayId;
}
