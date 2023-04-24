package com.jcohy.survey;

import java.time.LocalDate;

import com.jcohy.survey.controller.MessageHint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.jcohy.survey.utils.DateUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/4/19:17:47
 * @since 2022.0.1
 */
public class TestDate {

    @Test
    void date() {
        String s = LocalDate.now().toString();
        System.out.println(s);
    }

    @Test
    @Disabled
    void date2() {
        Assertions.assertTrue(DateUtils.isAfter("2022-04-19"));
        Assertions.assertTrue(DateUtils.isAfter("2022-04-18"));
        Assertions.assertTrue(DateUtils.isAfter("2022-03-21"));
        Assertions.assertFalse(DateUtils.isAfter("2022-03-20"));
    }

    @Test
    void test() {
        System.out.println(MessageHint.getMessage(10000));
        System.out.println(MessageHint.getMessage(30000));
        System.out.println(MessageHint.getMessage(50000));
        System.out.println(MessageHint.getMessage(100000));
        System.out.println(MessageHint.getMessage(200000));
    }
}
