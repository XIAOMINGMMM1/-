package cn.wtu.sj.entity;

import lombok.Data;

@Data
public class Notice {
    private Integer noticeId;
    private String title;
    private String content;
    private String images;
    private Integer rank;
}
