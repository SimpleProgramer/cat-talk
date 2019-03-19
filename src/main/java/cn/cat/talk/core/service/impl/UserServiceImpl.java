package cn.cat.talk.core.service.impl;

import cn.cat.talk.core.entity.User;
import cn.cat.talk.core.example.UserExample;
import cn.cat.talk.core.service.UserService;
import com.stip.mybatis.generator.plugin.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseService<User, UserExample, Long> implements UserService {
}