package cn.cat.talk.core.service;

import cn.cat.talk.core.entity.User;
import cn.cat.talk.core.example.UserExample;
import com.stip.mybatis.generator.plugin.IService;

 /**
 * Extensible custom interface
 **/
public interface UserService extends IService<User, UserExample, Long> {
}