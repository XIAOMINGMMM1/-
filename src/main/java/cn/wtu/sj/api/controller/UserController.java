package cn.wtu.sj.api.controller;

import cn.wtu.sj.api.dto.UserDTO;
import cn.wtu.sj.entity.User;
import cn.wtu.sj.mapper.UserMapper;
import cn.wtu.sj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 14:46
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;



    @PostMapping("/public-api/login")
    public String login(String userName, String passWord, Model model){
        User user = userService.checkPassWord(userName, passWord);
        if (user == null){
            model.addAttribute("errorMessage", "用户名或密码错误");
            return "public/index";
        }
        return "public/hello";
    }


    @RequestMapping("/public/user/exist-username")
    public ResponseEntity<String> judgeUserNameIsExist(String userName){
        if (StringUtils.isEmpty(userName)){
            return ResponseEntity.ok("false");
        }
        User user = userMapper.selectByUserName(userName);
        if (user != null){
            return ResponseEntity.ok("false");
        }else {
            return ResponseEntity.ok("true");
        }
    }

    @PostMapping("/public/user/save")
    public ResponseEntity<String> saveUser(UserDTO userDTO){
        String s = userService.saveUser(userDTO);
        System.out.println(s);
        return ResponseEntity.ok(s);
    }

    @PostMapping("/ordinary/user/update-password")
    @ResponseBody
    public ResponseEntity<String> updatePasswordById(User user){
        String s = userService.updatePasswordById(user);
        return ResponseEntity.ok(s);

    }

    @PostMapping("/ordinary/user/update-user-info")
    @ResponseBody
    public ResponseEntity<String> updateUserInfo(User user){
        String s = userService.updateUserInfo(user);
        return ResponseEntity.ok(s);
    }

    @GetMapping("/admin/user/reset-password")
    @ResponseBody
    public ResponseEntity<String> resetPassword(Integer id){
        User user = new User();
        user.setId(id);
        user.setPassWord("wtu123");
        userMapper.updatePasswordById(user);
        return ResponseEntity.ok("true");
    }


    @GetMapping("/admin/user/modif-enable")
    @ResponseBody
    public ResponseEntity<String> modifEnable(Integer id,String enable){
        User user = new User();
        user.setId(id);
        user.setEnable(enable);
        userMapper.updateEnableById(user);
        return ResponseEntity.ok("true");
    }


}
