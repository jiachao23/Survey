package com.jcohy.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jcohy.survey.SurveyException;
import com.jcohy.survey.service.Student;
import com.jcohy.survey.service.StudentRepository;

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
	public String index() {
		return "form";
	}

	@GetMapping("/report")
	public String report() {
		return "report";
	}

	@PostMapping("/submit")
	public String submit(@Validated Student student) {
		if (student.getReadCount() > 200000) {
			if (!StringUtils.hasLength(student.getComment())) {
				logger.info("添加失败！ {}", student);
				throw new SurveyException("阅读数字超过 200000，请在下方提供阅读的具体书名及阅读页码。");
			}
		}
		Student dbStudent = repository.findAllByUsernameAndDateAndClassName(student.getUsername(), student.getDate(),student.getClassName());
		if (dbStudent != null) {
			dbStudent.setClassName(student.getClassName());
			dbStudent.setDate(student.getDate());
			dbStudent.setUsername(student.getUsername());
			dbStudent.setReadCount(student.getReadCount());
			dbStudent.setComment(student.getComment());
			logger.info("updateStudent: {} ", student);
			repository.save(dbStudent);
		}
		else {
			logger.info("insertStudent: {} ", student);
			repository.save(student);
		}
		return "redirect:/success";
	}

	@GetMapping("/success")
	public String success() {
		return "success";
	}

	@GetMapping("/error")
	public String fail() {
		return "error";
	}
}
