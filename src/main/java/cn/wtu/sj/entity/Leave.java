package cn.wtu.sj.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 留言类
 * @author huiming.wang@hand-china.com
 * @date 2020/2/12 20:08
 */
@Data
public class Leave {
    private Integer leaveId;
    private Integer userId;
    private String leaveMessage;
    private Integer leaveUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String createTimeMeaning;

    private String userName;
}
