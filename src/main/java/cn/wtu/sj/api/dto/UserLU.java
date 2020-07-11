package cn.wtu.sj.api.dto;

import cn.wtu.sj.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserLU {

    private Integer count;
    private List<User> data;

}
