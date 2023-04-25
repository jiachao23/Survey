package com.jcohy.survey.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jcohy.survey.domain.ClassProjection;
import com.jcohy.survey.domain.UserProjection;
import com.jcohy.survey.service.Student;
import com.jcohy.survey.service.StudentRepository;
import com.jcohy.survey.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/4/25 11:24
 * @since 1.0.0
 */
@Component
public class ScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    private final StudentRepository repository;

    public ScheduleTask(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * （1）各年级每天阅读量前10名的学生信息可以随时更新、查看，并公开显示（包括班级、姓名和阅读量）；
     */
    public void eachDayTop10() {
        List<Student> students = repository.findAllByDate(DateUtils.now());
        Map<String,List<Student>> top10 = new HashMap<>(2);

        top10.put("三年级",students.stream()
                .filter(student -> student.getClassName().startsWith("三"))
                .sorted()
                .limit(10)
                .collect(Collectors.toList()));

        top10.put("四年级",students.stream()
                .filter(student -> student.getClassName().startsWith("四"))
                .sorted()
                .limit(10)
                .collect(Collectors.toList()));

        top10.forEach((key,value) -> {
            logger.info(key);
            value.forEach(System.out::println);
        });
    }

    /**
     * （2）各年级每月累计阅读量前20名的学生信息可以随时更新、查看，并公开显示（包括班级、姓名和阅读量）；
     */
    public void eachMonthClassTop20() {
        List<Student> students = repository.findAll()
                .stream()
                .filter(student -> DateUtils.isAfter(student.getDate()))
                .collect(Collectors.toList());

        Map<String,List<Student>> top20 = new HashMap<>(2);

        students.stream()
                .filter(student -> student.getClassName().startsWith("三"))
                .collect(Collectors.groupingBy(Student::getClassName))
                .forEach((className,value) -> {
                    List<Student> student = new ArrayList<>();
                    value.stream()
                            .collect(Collectors.groupingBy(Student::getUsername,Collectors.summarizingLong(Student::getReadCount)))
                            .forEach((username,readCount) -> {
                                Student stu = new Student();
                                stu.setUsername(username);
                                stu.setClassName(className);
                                stu.setReadCount(readCount.getSum());
                                student.add(stu);
                            });
                    top20.put("三年级",student.stream().sorted().limit(20).collect(Collectors.toList()));
                });

        students.stream()
                .filter(student -> student.getClassName().startsWith("四"))
                .collect(Collectors.groupingBy(Student::getClassName))
                .forEach((className,value) -> {
                    List<Student> student = new ArrayList<>();
                    value.stream()
                            .collect(Collectors.groupingBy(Student::getUsername,Collectors.summarizingLong(Student::getReadCount)))
                            .forEach((username,readCount) -> {
                                Student stu = new Student();
                                stu.setUsername(username);
                                stu.setClassName(className);
                                stu.setReadCount(readCount.getSum());
                                student.add(stu);
                            });
                    top20.put("四年级",student.stream().sorted().limit(20).collect(Collectors.toList()));
                });

        top20.forEach((key,value) -> {
            logger.info(key);
            value.forEach(System.out::println);
        });
    }
    /**
     * （3）可随时查看各班每天的人均阅读量（公开信息）及年级排名（需授权查看）；
     */
    public void eachDayClassAvg() {
        List<ClassProjection> projection = repository.findDayClassProjection(LocalDate.now().minusDays(1).toString());
        projection
                .stream()
                .collect(Collectors.groupingBy(classProjection -> classProjection.getName().startsWith("三")))
                .forEach((key,value) -> {
                    if(key) {
                        logger.info("三年级排行");
                    } else {
                        logger.info("四年级排行");
                    }
                    value.stream().forEach(classProjection -> {
                        logger.info(classProjection.getName() + ":" + classProjection.getReadCount());
                    });
                });
    }

    /**
     * （4）可随时查看各班每月累计的人均阅读量（公开信息）及年级排名（需授权查看）；
     */
    public void eachMonthClassAvg() {
        List<ClassProjection> projections = repository.findClassProjection();
        projections
                .stream()
                .collect(Collectors.groupingBy(classProjection -> classProjection.getName().startsWith("三")))
                .forEach((key,value) -> {
                    if(key) {
                        logger.info("三年级排行");
                    } else {
                        logger.info("四年级排行");
                    }
                    value.stream().forEach(classProjection -> {
                        logger.info(classProjection.getName() + ":" + classProjection.getReadCount());
                    });
                });
    }

    /**
     * （5）班主任可随时查看本班每个孩子每天和每月累计的阅读量、排名、名次变化；
     */
    public void eachClassStudentCount(String className) {
        logger.info(className + "今日统计排行");
        List<UserProjection> dayStudent = repository.findAllByClassNameAndDate(className, LocalDate.now().minusDays(1).toString());
        dayStudent.forEach(student -> {
            logger.info(student.getUsername() + ":\t" + student.getReadCount());
        });

        logger.info(className + "累计统计排行");
        List<UserProjection> monthStudent = repository.findAllByClassName(className);
        monthStudent.forEach(student -> {
            logger.info(student.getUsername() + ":\t" + student.getReadCount());
        });
    }

    /**
     * （6）家长可以随时查看自己孩子每天和每月累计的阅读量及在班级排名、名次变化；
     */
    public void eachStudentCount(String username) {
        Student student = repository.findAllByUsernameAndDate(username, DateUtils.now());
        logger.info("{} 今日阅读量: {}", username,student.getReadCount());

        long sum = repository.findAllByUsername(username)
                .stream()
                .mapToLong(Student::getReadCount)
                .sum();
        logger.info("{} 累计阅读量: {}", username,sum);
    }
}
