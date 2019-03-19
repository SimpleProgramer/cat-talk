package cn.cat.talk;

import cn.cat.talk.socket.CatTalkServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"cn.cat.talk.core.dao.*,com.stip.mybatis.generator.plugin.GenericMapper"})
public class CatTalkApplication {

    public static void main(String[] args) throws InterruptedException {
        new CatTalkServer().bind(8080);
        SpringApplication.run(CatTalkApplication.class, args);
    }
}
