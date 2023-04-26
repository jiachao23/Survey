package com.jcohy.survey.enums;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/4/24 18:08
 * @since 1.0.0
 */
public enum MessageHint {

    ONE(0,10000,"你是阅读小蜗牛，加油加油加油呀！"),
    TWO(10000,30000,"你是阅读小毛虫，爬呀爬呀快快爬！"),

    THREE(30000,50000,"你是阅读小蜜蜂，飞呀飞呀快快飞！"),
    FOUR(50000,100000,"你是阅读小兔子，追呀追呀我要追！"),
    FIVE(100000,300000,"你是阅读达人，我对你顶礼膜拜！")
    ;

    private Integer min;

    private Integer max;

    private String message;

    MessageHint(Integer min, Integer max, String message) {
        this.min = min;
        this.max = max;
        this.message = message;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(Integer count) {
        for(MessageHint hint : MessageHint.values()) {
            if(hint.getMin() <= count && count < hint.getMax()) {
                return hint.getMessage();
            }
        }
        return "";
    }
}
