package cn.wtu.sj.utils;

import cn.wtu.sj.entity.Leave;
import cn.wtu.sj.entity.User;
import cn.wtu.sj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/13 17:13
 */
@Component
public class MyUtils {
    @Autowired
    private UserService userService;

    private static UserService staticUserService;

    @PostConstruct
    public void init() {
        MyUtils.staticUserService=userService;
    }


    // 解密
    public static String decode(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static List<Leave>  addDateString(List<Leave> leaves){
        ArrayList<Leave> result = new ArrayList<>();
        for (Leave leave : leaves) {
            LocalDateTime createTime = leave.getCreateTime();
            String s = createTime.toString();
            s = s.replace("T", " ");
            leave.setCreateTimeMeaning(s);
            result.add(leave);
        }
        return result;
    }

    public static User checkLogin(){
        //当前登录用户信息放入
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
           UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            User user = staticUserService.selectUserByName(userDetails.getUsername());
            return user;
        }else {
           return null;
        }
    }

}
