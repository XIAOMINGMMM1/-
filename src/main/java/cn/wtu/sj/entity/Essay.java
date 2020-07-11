package cn.wtu.sj.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/6 15:44
 */
@Data
public class Essay {
    private Integer essayId;
    private Integer userId;
    private String title;
    private String images;
    private String info;
    private Date createTime;
    private Date updateTime;
    private Integer like;
    private Integer commentNum;
    private String UserName;
    private Integer reportNum;
}
