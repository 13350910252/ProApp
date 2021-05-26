package com.yinp.tools.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DateUtils {
    public static class scrollDate {
        public static final int DEFAULT_START_YEAR = 1990;

        /**
         * 默认是1990到当前年
         *
         * @return
         */
        public static List<String> setYear() {
            return setYear(DEFAULT_START_YEAR);
        }

        public static List<String> setYear(int startYear) {
            Calendar calendar = Calendar.getInstance();
            int curYear = calendar.get(Calendar.YEAR);
            return setYear(startYear, curYear);
        }

        public static List<String> setYear(int startYear, int endYear) {
            if (startYear > endYear || startYear < DEFAULT_START_YEAR) {
                return null;
            }
            List<String> list = new ArrayList<>();
            for (int i = startYear; i <= endYear; i++) {
                list.add(String.valueOf(i));
            }
            return list;
        }

        /**
         * 默认1到12月
         *
         * @return
         */
        public static List<String> setMonth() {
            return setMonth(1, 12);
        }

        public static List<String> setMonth(int startMonth, int endMonth) {
            if (startMonth > endMonth || startMonth < 1 || endMonth > 12) {
                return null;
            }
            List<String> list = new ArrayList<>();
            for (int i = startMonth; i <= endMonth; i++) {
                list.add(String.valueOf(i));
            }
            return list;
        }

        /**
         * 日期会随着年和月的改变而改变
         *
         * @param year
         * @param month
         * @return
         */
        public static List<String> setDay(int year, int month) {
            List<Integer> _31Days = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));
            List<String> list = new ArrayList<>();
            int days;
            if (_31Days.contains(month)) {
                days = 31;
            } else if (month == 2) {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
                    days = 29;
                } else {
                    days = 28;
                }
            } else {
                days = 30;
            }
            for (int i = 1; i <= days; i++) {
                list.add(String.valueOf(i));
            }
            return list;
        }
    }
}
