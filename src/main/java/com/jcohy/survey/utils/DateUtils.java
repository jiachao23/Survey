package com.jcohy.survey.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/4/19:16:58
 * @since 2022.0.1
 */
public class DateUtils {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static String now() {
		return LocalDate.now().toString();
	}

	public static boolean isAfter(String date) {
		LocalDate localDate = LocalDate.parse(date, formatter);
		LocalDate minusDays = LocalDate.now().minusDays(30);
		return localDate.isAfter(minusDays);
	}

	public static LocalDate parse(String date) {
		try {
			return LocalDate.parse(date, formatter);
		}
		catch (Exception exception) {
			return null;
		}
	}
}
