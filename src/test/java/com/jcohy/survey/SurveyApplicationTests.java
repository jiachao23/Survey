package com.jcohy.survey;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jcohy.survey.service.Student;
import com.jcohy.survey.service.StudentRepository;
import com.jcohy.survey.utils.DateUtils;

@SpringBootTest
@Disabled
class SurveyApplicationTests {

	@Autowired
	private StudentRepository repository;


	@Test
	void contextLoads() {
	}


	@Test
	void getStudent() {
		List<Student> students = repository.findAllByDate(DateUtils.now());

//        Map<String, List<Student>> listMap = students.stream()
//                .collect(Collectors.groupingBy(Student::getClassName));
//        listMap.forEach((key,value) -> {
//            System.out.println(key + ":::" + value.toString());
//        });
//        repository.findAllByDate(LocalDate.now().toString())
//                .forEach(System.out::println);

	}

}
