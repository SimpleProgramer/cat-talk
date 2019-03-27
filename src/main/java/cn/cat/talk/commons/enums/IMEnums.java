package cn.cat.talk.commons.enums;

/**
 * @author wangzun
 * @version 2019/3/26 下午4:43
 * @desc
 */
public enum  IMEnums {
    ONLINE(1),
    STARTCHAT(2),
    CHATING(3);
    /**
     * 错误码code
     */
    private int type;

    IMEnums(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
