package com.jcohy.survey.service;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.jcohy.survey.validation.DateConstraint;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/4/19:16:59
 * @since 2022.0.1
 */
@Entity
public class Student implements Comparable<Student> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "班级不能为空！")
    private String className;

    @DateConstraint(message = "日期格式必须为 yyyy-mm-dd")
    private String date;

    @NotNull(message = "阅读量不能为空")
    @Digits(integer = 10, fraction = 0)
    private Long readCount = 0L;

    @NotNull(message = "姓名不能为空")
    private String username;

    private String time;

    private String timeCount;

    private String comment = "";

    public String getComment() {
        return comment;
    }

    public Student setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Student setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Student setId(Long id) {
        this.id = id;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Student setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Student setDate(String date) {
        this.date = date;
        return this;
    }

    public Long getReadCount() {
        return readCount;
    }

    public Student setReadCount(Long readCount) {
        this.readCount = readCount;
        return this;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(String timeCount) {
        this.timeCount = timeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(className, student.className) && Objects.equals(date, student.date) && Objects.equals(readCount, student.readCount) && Objects.equals(username, student.username) && Objects.equals(time, student.time) && Objects.equals(timeCount, student.timeCount) && Objects.equals(comment, student.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, className, date, readCount, username, time, timeCount, comment);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", date='" + date + '\'' +
                ", readCount=" + readCount +
                ", username='" + username + '\'' +
                ", time='" + time + '\'' +
                ", timeCount='" + timeCount + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return Long.compare(o.getReadCount(), this.getReadCount());
    }
}
