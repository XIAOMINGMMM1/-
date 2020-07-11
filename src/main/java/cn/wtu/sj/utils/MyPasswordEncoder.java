package cn.wtu.sj.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 21:47
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.toString().equals(s);
    }
}
