package com.jcohy.survey.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findAllByDate(String Date);

    Student findAllByUsernameAndDate(String username,String date);
}
