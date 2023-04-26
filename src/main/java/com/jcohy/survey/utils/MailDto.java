package com.jcohy.survey.utils;

import java.util.List;
import java.util.Map;

import com.jcohy.survey.domain.ClassReadCount;
import com.jcohy.survey.service.Student;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/4/25 17:27
 * @since 1.0.0
 */
public class MailDto {

    private Integer type;

    private String title;

    private Map<String, List<Student>> listMap;

    private List<ClassReadCount> classProjections;

    public List<ClassReadCount> getClassProjections() {
        return classProjections;
    }

    public void setClassProjections(List<ClassReadCount> classProjections) {
        this.classProjections = classProjections;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, List<Student>> getListMap() {
        return listMap;
    }

    public void setListMap(Map<String, List<Student>> listMap) {
        this.listMap = listMap;
    }
}
