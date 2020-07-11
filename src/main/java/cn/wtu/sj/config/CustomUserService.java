package cn.wtu.sj.config;

import cn.wtu.sj.entity.User;
import cn.wtu.sj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 17:10
 */
@Component
public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        //用户权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (!StringUtils.isEmpty(user.getRoles())){
            String[] split = user.getRoles().split(",");
            for (String s : split) {
                authorities.add(new SimpleGrantedAuthority(s));
            }
        }else {
            //使用默认角色
            authorities.add(new SimpleGrantedAuthority("ROLE_ordinary"));
            if (user.getId()==-1){
                authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassWord(), authorities);
    }

}
