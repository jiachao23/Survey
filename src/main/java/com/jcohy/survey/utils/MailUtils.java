package com.jcohy.survey.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.mail.internet.MimeMessage;

import com.jcohy.survey.controller.ScheduleTask;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/4/25 16:04
 * @since 1.0.0
 */
@Component
public class MailUtils {

    private final JavaMailSender mailSender;

    private final ScheduleTask task;
    public MailUtils(JavaMailSender mailSender, ScheduleTask task) {
        this.mailSender = mailSender;
        this.task = task;
    }

    public void send(List<MailDto> dtos) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            message.setSubject(LocalDate.now().minusDays(1).toString() + " 统计");
            String s = buildHtmlContent(dtos);
            helper.setText(s,true);
            helper.setTo("jia_chao1203@126.com");
            helper.setFrom("jia_chao1203@126.com");
            mailSender.send(message);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String buildHtmlContent(List<MailDto> dtos) throws IOException {
        StringBuffer tableBuffer = buildTableContent(dtos);
//        StringBuffer buffer = getHtmlContent();
        return tableBuffer.toString();
    }

    private StringBuffer buildTableContent(List<MailDto> dtos) {

        StringBuffer sb = new StringBuffer();

        sb.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "</head>\n"
                + "<body>");


        dtos.forEach(mailDto -> {
            sb.append("<h1 style=\"text-align: left;margin-bottom:10px\">标题: "+mailDto.getTitle()+"</h1>");
            sb.append("<div style=\"text-align: left; \">");
            mailDto.listMap.forEach((name,value) -> {
                sb.append("<table border=1 cellPadding=0 cellSpacing=0 width=\"700px\" style=\"text-indent:5px;margin-bottom:20px;border-color:black;\">");
                sb.append("<thead><tr height='40px'>" +
                        "			<td with='20%'>年级</td>" +
                        "			<td with='35%'>姓名</td>" +
                        "			<td with='40%'>阅读量</td>" +
                        "		</tr></thead>");
                value.forEach(student -> {
                    sb.append("<tr height='40px'>");
                    sb.append("<td>" + student.getClassName() + "</td>");
                    sb.append("<td>" + student.getUsername() + "</td>");
                    sb.append("<td>" + student.getReadCount() + "</td>");
                    sb.append("</tr>");
                });
            });

            sb.append("</table>");
            sb.append("</div>");
        });
        sb.append("</body>\n"
                + "</html>");
        return sb;
    }
}
