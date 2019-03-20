package cn.cat.talk;

import cn.cat.talk.socket.CatTalkServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "cn.cat.talk.core.dao.*")
public class CatTalkApplication {

    public static void main(String[] args) throws InterruptedException {

        SpringApplication.run(CatTalkApplication.class, args);
        new Thread(() -> {
            try {
                new CatTalkServer().bind(8080);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
