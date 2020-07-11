package cn.wtu.sj.mapper;

import cn.wtu.sj.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 13:00
 */
@Repository
public interface UserMapper{
    User selectByUserName(@Param("userName") String userName);

    void save(User user);

    void updatePasswordById(User user);

    List<User> selectAll(@Param("userName") String userName);

    void updateEnableById(User user);
}
