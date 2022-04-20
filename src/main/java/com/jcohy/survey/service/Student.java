package com.jcohy.survey.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

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
public class Student implements Comparable<Student>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;


    private String date;

    private Integer readCount;

    private String username;

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

    public Integer getReadCount() {
        return readCount;
    }

    public Student setReadCount(Integer readCount) {
        this.readCount = readCount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getClassName(), student.getClassName()) && Objects.equals(getDate(), student.getDate()) && Objects.equals(getReadCount(), student.getReadCount()) && Objects.equals(getUsername(), student.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClassName(), getDate(), getReadCount(), getUsername());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", date='" + date + '\'' +
                ", readCount=" + readCount +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return this.getReadCount().compareTo(o.getReadCount());
    }
}
