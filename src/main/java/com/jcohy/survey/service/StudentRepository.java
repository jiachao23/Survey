package com.jcohy.survey.service;

import java.util.List;

import com.jcohy.survey.domain.ClassProjection;
import com.jcohy.survey.domain.UserProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/4/19:17:04
 * @since 2022.0.1
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByUsername(String username);

    Student findAllByUsernameAndDate(String username,String date);

    List<Student> findAllByDate(String Date);

    /**
     * 每个孩子每月累计的阅读量
     */
    @Query("SELECT username as username, sum(readCount) as readCount FROM Student where className=?1 GROUP BY username order by readCount desc")
    List<UserProjection> findAllByClassName(String className);

    /**
     * 每个孩子每月累计的阅读量
     */
    @Query("SELECT username as username, sum(readCount) as readCount FROM Student where className=?1 and date = ?2 GROUP BY username order by readCount desc ")
    List<UserProjection> findAllByClassNameAndDate(String className,String Date);

    /**
     * 获取学生信息
     */
    Student findAllByUsernameAndDateAndClassName(String username, String date,String className);

    /**
     * 各班每天的人均阅读量
     */
    @Query(value = "SELECT className as name,avg(readCount) as readCount,date as date FROM Student where date= ?1 GROUP BY className  order by readCount desc")
    List<ClassProjection> findDayClassProjection(String date);

    /**
     * 各班每月的人均阅读量
     */
    @Query(value = "SELECT className as name,avg(readCount) as readCount,date as date FROM Student GROUP BY className order by readCount desc")
    List<ClassProjection> findClassProjection();

}
