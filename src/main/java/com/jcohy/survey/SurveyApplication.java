package com.jcohy.survey;

import java.util.List;
import java.util.Map;

import com.jcohy.survey.controller.ScheduleTask;
import com.jcohy.survey.domain.ClassReadCount;
import com.jcohy.survey.service.Student;
import com.jcohy.survey.utils.MailDto;
import com.jcohy.survey.utils.MailUtils;

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
    public CommandLineRunner commandLineRunner(ScheduleTask task, MailUtils mailUtils) {
        return args -> {
            Map<String, List<Student>> top10 = task.eachDayTop10();
            MailDto top10Mail = new MailDto();
            top10Mail.setTitle("各年级昨日阅读量 Top10");
            top10Mail.setType(1);
            top10Mail.setListMap(top10);

            Map<String, List<Student>> top20 = task.eachMonthClassTop20();
            MailDto top20Mail = new MailDto();
            top20Mail.setTitle("各年级月累计阅读量 Top20");
            top20Mail.setType(1);
            top20Mail.setListMap(top20);

            List<ClassReadCount> eachDayClassAvgProjections = task.eachDayClassAvg();
            MailDto eachDayClassAvg = new MailDto();
            eachDayClassAvg.setType(2);
            eachDayClassAvg.setTitle("各班人均阅读量");
            eachDayClassAvg.setClassProjections(eachDayClassAvgProjections);

            List<ClassReadCount> eachMonthClassAvgProjections1 = task.eachMonthClassAvg();
            MailDto eachMonthClassAvg = new MailDto();
            eachMonthClassAvg.setType(2);
            eachMonthClassAvg.setTitle("各班月累计人均阅读量");
            eachMonthClassAvg.setClassProjections(eachMonthClassAvgProjections1);


            List<ClassReadCount> classYesterdaySum = task.classYesterdaySum();
            MailDto classYesterdaySumDto = new MailDto();
            classYesterdaySumDto.setType(3);
            classYesterdaySumDto.setTitle("班级昨日总阅读量排行榜");
            classYesterdaySumDto.setClassProjections(classYesterdaySum);

            List<ClassReadCount> classAllSum = task.classAllSum();
            MailDto classAllSumDto = new MailDto();
            classAllSumDto.setType(3);
            classAllSumDto.setTitle("班级累计总阅读量排行榜");
            classAllSumDto.setClassProjections(classAllSum);

            mailUtils.send(List.of(top10Mail, top20Mail, eachDayClassAvg, eachMonthClassAvg, classYesterdaySumDto, classAllSumDto));
        };
    }
}
