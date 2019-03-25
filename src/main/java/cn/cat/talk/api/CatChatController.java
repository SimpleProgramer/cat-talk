package cn.cat.talk.api;

import cn.cat.talk.commons.beans.ApiResponse;
import cn.cat.talk.core.pojo.resp.ChatResp;
import cn.cat.talk.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangzun
 * @version 2019/3/25 上午9:40
 * @desc
 */
@RestController
public class CatChatController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/chatlist")
    public ApiResponse<List<ChatResp>> chatList(long account) {
        return apiResponse(userService.findChatList(account));
    }
}
