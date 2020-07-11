package cn.wtu.sj;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 11:36
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@SpringBootApplication
@MapperScan("cn.wtu.sj.mapper")
@Controller
public class Application {
    public static void main(String[] args) {
       // SpringApplication.run(Application.class, args);
        Optional<Object> o = Optional.ofNullable(null);
        boolean present = o.isPresent();
        o.orElse(1);
        System.out.println();

    }

}
