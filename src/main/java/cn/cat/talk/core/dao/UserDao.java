package cn.cat.talk.core.dao;

import cn.cat.talk.core.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 可添加自定义查询语句，方便后续扩展
 **/
@Mapper
public interface UserDao  {
    @Select("select * from user where account = #{account} and delete_status = 1")
    User findByAccount(@Param("account") Long fromAccount);
}