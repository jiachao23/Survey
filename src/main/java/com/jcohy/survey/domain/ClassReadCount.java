package com.jcohy.survey.domain;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/4/26 12:25
 * @since 1.0.0
 */
public class ClassReadCount {

    private String name;

    private String readCount;

    private String date = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
