package cn.cat.talk.commons.enums;

/**
 * @author Twohoursperday
 * @create 2018-12-18 16:23
 * simple description of this file
 */
public enum DateFormatterEnum {
    SIMPLE_YMDHMS("YYYYMMddHHmmss"),
    YMDHMS("yyyy-MM-dd HH:mm:ss"),
    YM("yyyy-MM"),
    YMD("yyyy-MM-dd"),
    YMD2("yyyy.MM.dd"),
    HM("HH:mm");

    private String style;

    DateFormatterEnum(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
