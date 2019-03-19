package cn.cat.talk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.cat.talk.core.dao.*")
public class CatTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatTalkApplication.class, args);
    }
}
