package com.jcohy.survey;

import java.util.List;
import java.util.Map;

import com.jcohy.survey.controller.ScheduleTask;
import com.jcohy.survey.service.Student;
import com.jcohy.survey.utils.MailDto;
import com.jcohy.survey.utils.MailUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SurveyApplication implements CommandLineRunner {


    @Autowired
    private MailUtils utils;

    @Autowired
    private ScheduleTask task;

    public static void main(String[] args) {
        SpringApplication.run(SurveyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, List<Student>> stringListMap = task.eachDayTop10();
        MailDto mailDto = new MailDto();
        mailDto.setTitle("各年级每天阅读量 Top10");
        mailDto.setListMap(stringListMap);
        utils.send(List.of(mailDto));
    }
}
