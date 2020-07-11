package cn.wtu.sj.api.dto;

import lombok.Data;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/16 14:12
 */
@Data
public class UserDTO {
    private String userName;
    private String nickName;
    private String password;
    private String realName;
}
