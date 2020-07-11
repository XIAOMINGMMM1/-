package cn.wtu.sj.service;

import cn.wtu.sj.api.dto.UserDTO;
import cn.wtu.sj.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 14:23
 */
public interface UserService {
    /**
     * 根据用户名查询用户信息
     * @param UserName
     * @return
     */
    User selectUserByName(String UserName);

    /**
     * 校验用户名和密码
     * @param userName
     * @param passWord
     * @return
     */
    User checkPassWord(String userName,String passWord);

    String saveUser(UserDTO userDTO);

    String updatePasswordById(User user);

    String updateUserInfo(User user);

    PageInfo<User> selectAll(Integer pageNum, Integer pageSize, String userName);

}
