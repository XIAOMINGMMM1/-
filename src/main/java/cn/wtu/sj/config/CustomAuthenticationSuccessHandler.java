package cn.wtu.sj.config;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/13 13:53
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String parameter = request.getQueryString();
        if (!StringUtils.isEmpty(parameter)){
            return parameter.replaceFirst("redirectUrl=","");
        }
//        if (!StringUtils.isEmpty(parameter)){
//            return parameter;
//        }

 //       HttpSession session = request.getSession();
        String redirectUrl = "";
//        try {
//             redirectUrl = (String) session.getAttribute("redirectUrl");
//        }catch (NullPointerException e){
//            System.out.println("----------------");
//        }
//        //清除重定向的地址
//        session.setAttribute("redirectUrl","");

//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length > 0) {
//            for (Cookie cookie : cookies) {
//                if ("redirectUrl".equals(cookie.getName())) {
//                    redirectUrl = MyUtils.decode(cookie.getValue());
//                }
//            }
//        }
//
//        Cookie cookie = new Cookie("redirectUrl", "");
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);

        if (!StringUtils.isEmpty(redirectUrl)){
            try {
                response.sendRedirect(redirectUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            return "/public/index.html";
        }
        return super.determineTargetUrl(request, response);
    }
}
