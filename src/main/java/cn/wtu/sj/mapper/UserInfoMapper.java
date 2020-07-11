package cn.wtu.sj.mapper;

import cn.wtu.sj.entity.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/16 14:33
 */
@Repository
public interface UserInfoMapper {
    void save(UserInfo userInfo);
    void update(UserInfo userInfo);
}
