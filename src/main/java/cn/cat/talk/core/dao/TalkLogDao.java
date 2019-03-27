package cn.cat.talk.core.dao;

import cn.cat.talk.core.entity.TalkLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangzun
 * @version 2019/3/25 上午10:41
 * @desc
 */
@Mapper
public interface TalkLogDao {
    @Insert("insert into  talk_log ()values()")
    int addChatLog(@Param("talkLog") TalkLog talkLog);
}
