package com.jcohy.survey;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jcohy.survey.controller.ScheduleTask;
import com.jcohy.survey.domain.ClassProjection;
import com.jcohy.survey.service.StudentRepository;
import com.jcohy.survey.utils.DateUtils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SurveyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ScheduleTask task) {
        return args -> {
            System.out.println("各班每天的人均阅读量（公开信息）及年级排名（需授权查看）");
            task.eachClassStudentCount("四年级（6）班");

        };
    }
}
