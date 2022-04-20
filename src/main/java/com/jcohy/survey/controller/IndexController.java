package com.jcohy.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcohy.survey.service.Student;
import com.jcohy.survey.service.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/4/19:15:46
 * @since 2022.0.1
 */
@Controller
public class IndexController {

	private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private StudentRepository repository;

    @GetMapping("/index")
    public String index(){
        return "form";
    }

    @GetMapping("/report")
    public String report() {
        return "report";
    }

    @PostMapping("/submit")
    public String submit(Student student) {
		logger.info(student.toString());
        Student dbStudent = repository.findAllByUsernameAndDate(student.getUsername(), student.getDate());
        if (dbStudent != null) {
            dbStudent.setClassName(student.getClassName());
            dbStudent.setDate(student.getDate());
            dbStudent.setUsername(student.getUsername());
            dbStudent.setReadCount(student.getReadCount());
            repository.save(dbStudent);
        } else {
            repository.save(student);
        }
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Student student) {
        return "success";
    }
}
