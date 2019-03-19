package cn.cat.talk.core.dao;

import cn.cat.talk.core.entity.User;
import cn.cat.talk.core.example.UserExample;
import com.stip.mybatis.generator.plugin.GenericMapper;

 /**
 * 可添加自定义查询语句，方便后续扩展
 **/
public interface UserDao extends GenericMapper<User, UserExample, Long> {
}