package cn.wtu.sj.service.impl;

import cn.wtu.sj.api.dto.UserDTO;
import cn.wtu.sj.entity.User;
import cn.wtu.sj.entity.UserInfo;
import cn.wtu.sj.mapper.UserInfoMapper;
import cn.wtu.sj.mapper.UserMapper;
import cn.wtu.sj.service.UserService;
import cn.wtu.sj.utils.MyUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 14:24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public User selectUserByName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public User checkPassWord(String userName, String passWord) {
        User user = userMapper.selectByUserName(userName);
        if (passWord == null || user == null){
            return null;
        }
        if (passWord.equals(user.getPassWord())){
            return user;
        }
        return null;

    }

    @Override
    public String saveUser(UserDTO userDTO) {
        if (StringUtils.isEmpty(userDTO.getUserName())){
            return "false";
        }
        User user = userMapper.selectByUserName(userDTO.getUserName());
        if (user != null){
            return "false";
        }

        user = new User();
        user.setUserName(userDTO.getUserName());
        user.setEnable("1");
        user.setPassWord(userDTO.getPassword());
        user.setRealName(userDTO.getRealName());
        userMapper.save(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNickname(userDTO.getNickName());
        userInfoMapper.save(userInfo);

        return "true";
    }

    @Override
    public String updatePasswordById(User user) {
        User u = MyUtils.checkLogin();
        if (u == null || !u.getId().equals(user.getId()) || StringUtils.isEmpty(user.getPassWord()) || !u.getPassWord().equals(user.getOldPassWord())){
            return "false";
        }
        userMapper.updatePasswordById(user);


        return "true";
    }

    @Override
    public String updateUserInfo(User user) {
        User u = MyUtils.checkLogin();
        if (u == null){
            return "false";
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(u.getId());
        userInfo.setNickname(user.getNickname());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhone(user.getPhone());
        userInfo.setIntro(user.getIntro());
        userInfoMapper.update(userInfo);

        return "true";
    }

    @Override
    public PageInfo<User> selectAll(Integer pageNum,Integer pageSize, String userName) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectAll(userName);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }
}
