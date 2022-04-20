package com.jcohy.survey.service;

import java.io.Serializable;
import java.util.List;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/4/19:18:37
 * @since 2022.0.1
 */
public class EchartsModel implements Serializable {

    private String title;

    private Category category;

    @Override
    public String toString() {
        return "EchartsModel{" +
                "title='" + title + '\'' +
                ", category=" + category +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public EchartsModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public EchartsModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public static class Category {

        private List<String> xAxis;

        private List<String> yAxis;

        private List<Integer> data;

        public List<String> getxAxis() {
            return xAxis;
        }

        public Category setxAxis(List<String> xAxis) {
            this.xAxis = xAxis;
            return this;
        }

        public List<String> getyAxis() {
            return yAxis;
        }

        public Category setyAxis(List<String> yAxis) {
            this.yAxis = yAxis;
            return this;
        }

        public List<Integer> getData() {
            return data;
        }

        public Category setData(List<Integer> data) {
            this.data = data;
            return this;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "xAxis=" + xAxis +
                    ", yAxis=" + yAxis +
                    ", data=" + data +
                    '}';
        }
    }
}
