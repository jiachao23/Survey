package com.jcohy.survey.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

import com.jcohy.survey.domain.ClassProjection;
import com.jcohy.survey.domain.ClassReadCount;
import com.jcohy.survey.domain.UserProjection;
import com.jcohy.survey.service.Student;
import com.jcohy.survey.service.StudentRepository;
import com.jcohy.survey.utils.DateUtils;
import com.jcohy.survey.utils.MailDto;
import com.jcohy.survey.utils.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
@EnableScheduling
public class ScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    private final StudentRepository repository;

    private final MailUtils mailUtils;

    public ScheduleTask(StudentRepository repository, MailUtils mailUtils) {
        this.repository = repository;
        this.mailUtils = mailUtils;
    }


    @Scheduled(cron = "0 0 2 * * ?")
//    @Scheduled(initialDelay = 3000,fixedDelay = 5000000)
    public void execute() {
        logger.info("开始执行定时任务，发送邮件");
        Map<String, List<Student>> top10 = this.eachDayTop10();
        MailDto top10Mail = new MailDto();
        top10Mail.setTitle("各年级昨日阅读量 Top10");
        top10Mail.setType(1);
        top10Mail.setListMap(top10);

        Map<String, List<Student>> top20 = this.eachMonthClassTop20();
        MailDto top20Mail = new MailDto();
        top20Mail.setTitle("各年级月累计阅读量 Top20");
        top20Mail.setType(1);
        top20Mail.setListMap(top20);

        List<ClassReadCount> eachDayClassAvgProjections = this.eachDayClassAvg();
        MailDto eachDayClassAvg = new MailDto();
        eachDayClassAvg.setType(2);
        eachDayClassAvg.setTitle("各班人均阅读量");
        eachDayClassAvg.setClassProjections(eachDayClassAvgProjections);

        List<ClassReadCount> eachMonthClassAvgProjections1 = this.eachMonthClassAvg();
        MailDto eachMonthClassAvg = new MailDto();
        eachMonthClassAvg.setType(2);
        eachMonthClassAvg.setTitle("各班月累计人均阅读量");
        eachMonthClassAvg.setClassProjections(eachMonthClassAvgProjections1);


        List<ClassReadCount> classYesterdaySum = this.classYesterdaySum();
        MailDto classYesterdaySumDto = new MailDto();
        classYesterdaySumDto.setType(3);
        classYesterdaySumDto.setTitle("班级昨日总阅读量排行榜");
        classYesterdaySumDto.setClassProjections(classYesterdaySum);

        List<ClassReadCount> classAllSum = this.classAllSum();
        MailDto classAllSumDto = new MailDto();
        classAllSumDto.setType(3);
        classAllSumDto.setTitle("班级累计总阅读量排行榜");
        classAllSumDto.setClassProjections(classAllSum);
        mailUtils.send(List.of(top10Mail, top20Mail, eachDayClassAvg, eachMonthClassAvg, classYesterdaySumDto, classAllSumDto));
        logger.info("邮件发送成功");
    }


    /**
     * （1）各年级每天阅读量前10名的学生信息可以随时更新、查看，并公开显示（包括班级、姓名和阅读量）；
     */
    public Map<String, List<Student>> eachDayTop10() {
        List<Student> students = repository.findAllByDate(LocalDate.now().minusDays(1).toString());
        Map<String, List<Student>> top10 = new HashMap<>(2);

        top10.put("三年级", students.stream()
                .filter(student -> student.getClassName().startsWith("三"))
                .sorted()
                .limit(10)
                .collect(Collectors.toList()));

        top10.put("四年级",students.stream()
                .filter(student -> student.getClassName().startsWith("四"))
                .sorted()
                .limit(10)
                .collect(Collectors.toList()));

        return top10;
    }

    /**
     * （2）各年级每月累计阅读量前20名的学生信息可以随时更新、查看，并公开显示（包括班级、姓名和阅读量）；
     */
    public Map<String,List<Student>>  eachMonthClassTop20() {
        List<Student> students = repository.findAll()
                .stream()
                .filter(student -> DateUtils.isAfter(student.getDate()))
                .collect(Collectors.toList());

        Map<String,List<Student>> top20 = new HashMap<>(2);

        List<Student> top203 = new ArrayList<>();
        students.stream()
                .filter(student -> student.getClassName().startsWith("三"))
                .collect(Collectors.groupingBy(Student::getClassName))
                .forEach((className,value) -> {

                    value.stream()
                            .collect(Collectors.groupingBy(Student::getUsername, Collectors.summarizingLong(Student::getReadCount)))
                            .forEach((username, readCount) -> {
                                Student stu = new Student();
                                stu.setUsername(username);
                                stu.setClassName(className);
                                stu.setReadCount(readCount.getSum());
                                top203.add(stu);
                            });

                });
        top20.put("三年级", top203.stream().sorted().limit(20).collect(Collectors.toList()));

        List<Student> top204 = new ArrayList<>();
        students.stream()
                .filter(student -> student.getClassName().startsWith("四"))
                .collect(Collectors.groupingBy(Student::getClassName))
                .forEach((className,value) -> {
                    value.stream()
                            .collect(Collectors.groupingBy(Student::getUsername, Collectors.summarizingLong(Student::getReadCount)))
                            .forEach((username, readCount) -> {
                                Student stu = new Student();
                                stu.setUsername(username);
                                stu.setClassName(className);
                                stu.setReadCount(readCount.getSum());
                                top204.add(stu);
                            });

                });
        top20.put("四年级", top204.stream().sorted().limit(20).collect(Collectors.toList()));

        return top20;
    }

    /**
     * （3）可随时查看各班每天的人均阅读量（公开信息）及年级排名（需授权查看）；
     */
    public List<ClassReadCount> eachDayClassAvg() {
        List<ClassProjection> projection = repository.findDayClassProjection(LocalDate.now().minusDays(1).toString());
        List<ClassReadCount> classReadCount = new ArrayList<>();
        projection
                .stream()
                .collect(Collectors.groupingBy(classProjection -> classProjection.getName().startsWith("三")))
                .forEach((key, value) -> {
                    if (key) {
                        logger.debug("三年级排行");
                    }
                    else {
                        logger.debug("四年级排行");
                    }
                    value.forEach(classProjection -> {
                        ClassReadCount countAvg = new ClassReadCount();
                        countAvg.setName(classProjection.getName());
                        countAvg.setReadCount(classProjection.getReadCount());
                        countAvg.setDate(classProjection.getDate());
                        classReadCount.add(countAvg);
                        logger.debug(classProjection.getName() + ":" + classProjection.getReadCount());
                    });
                });

        return classReadCount;
    }

    /**
     * （4）可随时查看各班每月累计的人均阅读量（公开信息）及年级排名（需授权查看）；
     */
    public List<ClassReadCount> eachMonthClassAvg() {
        List<ClassProjection> projections = repository.findClassProjection();
        List<ClassReadCount> classReadCount = new ArrayList<>();
        projections
                .stream()
                .collect(Collectors.groupingBy(classProjection -> classProjection.getName().startsWith("三")))
                .forEach((key, value) -> {
                    if (key) {
                        logger.debug("三年级排行");
                    }
                    else {
                        logger.debug("四年级排行");
                    }
                    value.forEach(classProjection -> {
                        ClassReadCount countAvg = new ClassReadCount();
                        countAvg.setName(classProjection.getName());
                        countAvg.setReadCount(classProjection.getReadCount());
                        countAvg.setDate(classProjection.getDate());
                        classReadCount.add(countAvg);
                        logger.debug(classProjection.getName() + ":" + classProjection.getReadCount());
                    });
                });
        return classReadCount;
    }

    /**
     * （5）班主任可随时查看本班每个孩子每天和每月累计的阅读量、排名、名次变化；
     */
    public void eachClassStudentCount(String className) {
        logger.debug(className + "今日统计排行");
        List<UserProjection> dayStudent = repository.findAllByClassNameAndDate(className, LocalDate.now().minusDays(1).toString());
        dayStudent.forEach(student -> {
            logger.debug(student.getUsername() + ":\t" + student.getReadCount());
        });

        logger.debug(className + "累计统计排行");
        List<UserProjection> monthStudent = repository.findAllByClassName(className);
        monthStudent.forEach(student -> {
            logger.debug(student.getUsername() + ":\t" + student.getReadCount());
        });
    }

    /**
     * （6）家长可以随时查看自己孩子每天和每月累计的阅读量及在班级排名、名次变化；
     */
    public void eachStudentCount(String username) {
        Student student = repository.findAllByUsernameAndDate(username, DateUtils.now());
        logger.debug("{} 今日阅读量: {}", username, student.getReadCount());

        long sum = repository.findAllByUsername(username)
                .stream()
                .mapToLong(Student::getReadCount)
                .sum();
        logger.debug("{} 累计阅读量: {}", username, sum);
    }


    /**
     * 班级累计总阅读量排行榜
     */
    public List<ClassReadCount> classAllSum() {

        List<ClassReadCount> classReadCount = new ArrayList<>();
        List<Student> allStudents = repository.findAll();
        Map<String, LongSummaryStatistics> collect = allStudents.stream()
                .filter((student) -> DateUtils.isAfter(student.getDate()))
                .collect(Collectors.groupingBy(Student::getClassName, Collectors.summarizingLong(Student::getReadCount)));

        List<Map.Entry<String, LongSummaryStatistics>> list = new ArrayList<>(collect.entrySet());

        list.sort((o1, o2) -> Long.compare(o2.getValue().getSum(), o1.getValue().getSum()));

        list.forEach((value) -> {
            ClassReadCount count = new ClassReadCount();
            count.setName(value.getKey());
            count.setReadCount(String.valueOf(value.getValue().getSum()));
            classReadCount.add(count);
        });

        return classReadCount;
    }


    /**
     * 班级昨日总阅读量排行榜
     */
    public List<ClassReadCount> classYesterdaySum() {

        List<ClassReadCount> classReadCount = new ArrayList<>();
        List<Student> yesterday = repository.findAllByDate(LocalDate.now().minusDays(1).toString());
        Map<String, LongSummaryStatistics> collect = yesterday.stream()
                .filter((student) -> DateUtils.isAfter(student.getDate()))
                .collect(Collectors.groupingBy(Student::getClassName, Collectors.summarizingLong(Student::getReadCount)));

        List<Map.Entry<String, LongSummaryStatistics>> list = new ArrayList<>(collect.entrySet());

        list.sort((o1, o2) -> Long.compare(o2.getValue().getSum(), o1.getValue().getSum()));

        list.forEach((value) -> {
            ClassReadCount count = new ClassReadCount();
            count.setName(value.getKey());
            count.setReadCount(String.valueOf(value.getValue().getSum()));
            classReadCount.add(count);
        });

        return classReadCount;
    }
}
