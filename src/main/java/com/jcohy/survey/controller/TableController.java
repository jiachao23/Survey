package com.jcohy.survey.controller;

import java.util.List;

import com.jcohy.survey.domain.TableDataInfo;
import com.jcohy.survey.service.Student;
import com.jcohy.survey.service.StudentRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/4/26 9:49
 * @since 1.0.0
 */
@Controller
public class TableController {


    private final StudentRepository repository;

    public TableController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/table")
    public String table(Model model) {
        return "table";
    }

    @PostMapping("/students")
    @ResponseBody
    public TableDataInfo student() {
        TableDataInfo rspData = new TableDataInfo();
        List<Student> students = repository.findAll();
        rspData.setCode(0);
        rspData.setRows(students);
        rspData.setTotal(students.size());
        return rspData;
    }

}
