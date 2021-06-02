package com.yinp.proapp.utils;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final ThreadLocal<SimpleDateFormat> Y_M_D_H_M_S_S = new ThreadLocal<SimpleDateFormat>() {
        @Nullable
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SS", Locale.getDefault());
        }
    };
    public static final ThreadLocal<SimpleDateFormat> yyyy_MM_dd = new ThreadLocal<SimpleDateFormat>() {
        @Nullable
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
    };
    public static final ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm = new ThreadLocal<SimpleDateFormat>() {
        @Nullable
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        }
    };

    /**
     * 格式日期
     *
     * @param time
     * @return
     */
    public static String getFormatDate(long time) {
        try {
            String format = "yyyy-MM-dd HH:mm";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat(format);
            Date date = new Date(time);
            return format1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 得到本机时间并转换成 年-月-日 时:分:秒 24小时制
     *
     * @return
     */
    public static String getFormatDate(Date date) {
        @SuppressLint("SimpleDateFormat") Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getFormatDate(String time) {
        try {
            String format = "yyyy-MM-dd HH:mm";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat(format);
            Date parse = format1.parse(time);
            assert parse != null;
            return format1.format(parse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字符串转化为对应格式的日期
     *
     * @param format
     * @param dateStr
     * @return
     * @description
     * @date 2014-7-22
     * @author zuolong
     */
    public static Date toDate(ThreadLocal<SimpleDateFormat> format,
                              String dateStr) {
        try {
            return format.get().parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static class DateAgo {
        /**
         * 以下是计算时间差值，返回值比如1秒前，5分钟前等等
         */
        private static final long ONE_MINUTE = 60000L;
        private static final long ONE_HOUR = 3600000L;
        private static final long ONE_DAY = 86400000L;
        private static final long ONE_WEEK = 604800000L;

        private static final String ONE_SECOND_AGO = "秒前";
        private static final String ONE_MINUTE_AGO = "分钟前";
        private static final String ONE_HOUR_AGO = "小时前";
        private static final String ONE_DAY_AGO = "天前";
        private static final String ONE_MONTH_AGO = "月前";
        private static final String ONE_YEAR_AGO = "年前";

        public static String format(Date date) {
            if (date == null) {
                return "";
            }
            long delta = new Date().getTime() - date.getTime();
            if (delta < 1L * ONE_MINUTE) {
                long seconds = toSeconds(delta);
                return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
            }
            if (delta < 45L * ONE_MINUTE) {
                long minutes = toMinutes(delta);
                return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
            }
            if (delta < 24L * ONE_HOUR) {
                long hours = toHours(delta);
                return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
            }
            if (delta < 48L * ONE_HOUR) {
                return "昨天";
            }
            if (delta < 30L * ONE_DAY) {
                long days = toDays(delta);
                return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
            }
            if (delta < 12L * 4L * ONE_WEEK) {
                long months = toMonths(delta);
                return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
            } else {
                long years = toYears(delta);
                return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
            }
        }

        private static long toSeconds(long date) {
            return date / 1000L;
        }

        private static long toMinutes(long date) {
            return toSeconds(date) / 60L;
        }

        private static long toHours(long date) {
            return toMinutes(date) / 60L;
        }

        private static long toDays(long date) {
            return toHours(date) / 24L;
        }

        private static long toMonths(long date) {
            return toDays(date) / 30L;
        }

        private static long toYears(long date) {
            return toMonths(date) / 365L;
        }
    }
}
