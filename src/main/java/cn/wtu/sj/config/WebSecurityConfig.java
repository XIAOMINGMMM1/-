package cn.wtu.sj.config;

import cn.wtu.sj.utils.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 17:03
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 验证用户的信息、处理注销
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/public/**", "/js/**", "/css/**", "/layui/**", "/res/**", "/imagesRes/**", "/img/**").permitAll()
                .antMatchers("/ordinary/**").hasRole("ordinary")
                .antMatchers("/admin/**").hasRole("admin")
                .and()
                .formLogin()
                .loginPage("/public/login.html")
                .loginProcessingUrl("/login")
                //.successForwardUrl("/public/index.html")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/public/login.html")
                .and()
                .csrf()
                .disable()
                .headers().frameOptions().disable();//解决iframe问题


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(new MyPasswordEncoder());
    }


    /**
     * 支持//请求
     * @return
     */
    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }
}
