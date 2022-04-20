package com.jcohy.survey.controller;

import com.jcohy.survey.service.EchartsModel;
import com.jcohy.survey.service.Student;
import com.jcohy.survey.service.StudentRepository;
import com.jcohy.survey.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/4/19:21:55
 * @since 2022.0.1
 */
@RestController
public class StatisticsController {

    @Autowired
    private StudentRepository repository;

    @PostMapping("/all")
    public List<EchartsModel> all(){

        List<EchartsModel> charts = new ArrayList<>(4);
        /**
         * 1.每天能够统计出每个学生累计阅读字数从高到低的排名及字数。
         * （可以显示出前20名学生的姓名、班级、累计阅读量）
         * 2.能够统计出每天每个班级的累计阅读字数排名。（能够显示出前三名班级及阅读总量）
         */
        charts.add(getDayStudentChart());

        charts.add(getDayClassChart());

        charts.add(getAllStudentChart());

        charts.add(getAllClassChart());

//        charts.stream().forEach(System.out::println);
//        System.out.println(charts.toString());
        return charts;
    }

    private EchartsModel getAllStudentChart() {
        EchartsModel monthStudentCharts = new EchartsModel();
        monthStudentCharts.setTitle("30 天内学生累计阅读字数排行");
        List<Student> allStudents = repository.findAll();

        List<String> yAxis = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        Map<String, IntSummaryStatistics> statisticsMap = allStudents.stream()
                .filter((student) -> DateUtils.isAfter(student.getDate()))
                .collect(Collectors.groupingBy(Student::getUsername, Collectors.summarizingInt(Student::getReadCount)));


        List<Map.Entry<String, IntSummaryStatistics>> list = new ArrayList<>(statisticsMap.entrySet());

        list.sort((o1, o2) -> (int) (o1.getValue().getSum() - o2.getValue().getSum()));

        list.forEach((l) -> {
            yAxis.add(l.getKey());
            data.add((int) l.getValue().getSum());
        });

        EchartsModel.Category category = new EchartsModel.Category();
        category.setyAxis(yAxis);
        category.setData(data);
        monthStudentCharts.setCategory(category);
        return monthStudentCharts;
    }

    private EchartsModel getAllClassChart() {
        List<Student> allStudents = repository.findAll();

        EchartsModel allClassCharts = new EchartsModel();
        allClassCharts.setTitle("30 天内班级累计阅读字数排行");

        EchartsModel.Category category = new EchartsModel.Category();
        List<String> yAxis = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        Map<String, IntSummaryStatistics> collect = allStudents.stream()
                .filter((student) -> DateUtils.isAfter(student.getDate()))
                .collect(Collectors.groupingBy(Student::getClassName, Collectors.summarizingInt(Student::getReadCount)));

        List<Map.Entry<String, IntSummaryStatistics>> list = new ArrayList<>(collect.entrySet());

        list.sort((o1, o2) -> (int) (o1.getValue().getSum() - o2.getValue().getSum()));

        list.forEach((l) -> {
            yAxis.add(l.getKey());
            data.add((int) l.getValue().getSum());
        });

        category.setyAxis(yAxis);
        category.setData(data);
        allClassCharts.setCategory(category);
        return allClassCharts;
    }

    private EchartsModel getDayClassChart() {
        List<Student> students = repository.findAllByDate(DateUtils.now());

        EchartsModel dayClassCharts = new EchartsModel();
        dayClassCharts.setTitle("当天班级累计阅读字数排行");
        EchartsModel.Category category = new EchartsModel.Category();
        List<String> yAxis = new ArrayList<>();
        List<Integer> data = new ArrayList<>();


        Map<String, IntSummaryStatistics> collect = students.stream()
                .collect(Collectors.groupingBy(Student::getClassName, Collectors.summarizingInt(Student::getReadCount)));

        List<Map.Entry<String, IntSummaryStatistics>> list = new ArrayList<>(collect.entrySet());

        list.sort((o1, o2) -> (int) (o1.getValue().getSum() - o2.getValue().getSum()));

        list.forEach((l) -> {
            yAxis.add(l.getKey());
            data.add((int) l.getValue().getSum());
        });

        category.setyAxis(yAxis);
        category.setData(data);
        dayClassCharts.setCategory(category);
        return dayClassCharts;
    }

    private EchartsModel getDayStudentChart() {
        List<Student> students = repository.findAllByDate(DateUtils.now());

        EchartsModel dayStudentCharts = new EchartsModel();
        dayStudentCharts.setTitle("当天学生累计阅读字数排行");

        List<Student> dayStudents = students.stream()
                .limit(20)
                .sorted().collect(Collectors.toList());

        EchartsModel.Category category = new EchartsModel.Category();
        category.setyAxis(getUserName(dayStudents));
        category.setData(getReadCount(dayStudents));
        dayStudentCharts.setCategory(category);
        return dayStudentCharts;
    }

    private List<String> getUserName(List<Student> students) {
        return students.stream()
                .map(Student::getUsername)
                .collect(Collectors.toList());
    }

    private List<Integer> getReadCount(List<Student> students) {
        return students.stream()
                .map(Student::getReadCount)
                .collect(Collectors.toList());
    }
}
