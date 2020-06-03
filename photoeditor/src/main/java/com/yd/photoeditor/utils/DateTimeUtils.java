package com.yd.photoeditor.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {
    private static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
    private static final String DATE_FORMAT_MM_DD_YYYY = "MM/dd/yyyy";
    public static final String DATE_FORMAT_STANDARDIZED_UTC = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT_GMT = "yyyy-MM-dd HH:mm:ss 'GMT'";
    public static final String DATE_TIME_FORMAT_STANDARDIZED_UTC = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentDateTimeGMT() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_GMT, Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(new Date());
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat(DATE_TIME_FORMAT_STANDARDIZED_UTC).format(new Date());
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat(DATE_FORMAT_STANDARDIZED_UTC).format(new Date());
    }

    public static Date convertGMTString(String str) {
        try {
            return new SimpleDateFormat(DATE_TIME_FORMAT_GMT).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertUTCString(String str) {
        try {
            return new SimpleDateFormat(DATE_TIME_FORMAT_STANDARDIZED_UTC).parse(str);
        } catch (ParseException unused) {
            try {
                return new SimpleDateFormat(DATE_TIME_FORMAT_STANDARDIZED_UTC).parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static boolean isGMTString(String str) {
        try {
            new SimpleDateFormat(DATE_TIME_FORMAT_GMT).parse(str);
            return true;
        } catch (ParseException unused) {
            return false;
        }
    }

    public static boolean isUTCString(String str) {
        try {
            new SimpleDateFormat(DATE_TIME_FORMAT_STANDARDIZED_UTC).parse(str);
            return true;
        } catch (ParseException unused) {
            return false;
        }
    }

    public static boolean isUTCDateString(String str) {
        try {
            new SimpleDateFormat(DATE_FORMAT_STANDARDIZED_UTC).parse(str);
            return true;
        } catch (ParseException unused) {
            return false;
        }
    }

    public static String toUTCDateString(String str) {
        try {
            return new SimpleDateFormat(DATE_FORMAT_STANDARDIZED_UTC).format(new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY).parse(str));
        } catch (ParseException unused) {
            try {
                return new SimpleDateFormat(DATE_FORMAT_STANDARDIZED_UTC).format(new SimpleDateFormat(DATE_FORMAT_MM_DD_YYYY).parse(str));
            } catch (ParseException unused2) {
                return getCurrentDate();
            }
        }
    }

    public static String toStandardizedUTCFromGMT(String str) {
        try {
            return new SimpleDateFormat(DATE_TIME_FORMAT_STANDARDIZED_UTC).format(new SimpleDateFormat(DATE_TIME_FORMAT_GMT).parse(str));
        } catch (ParseException unused) {
            return null;
        }
    }

    public static int compare(String str, String str2) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARDIZED_UTC);
        try {
            try {
                return simpleDateFormat.parse(str).compareTo(simpleDateFormat.parse(str2));
            } catch (ParseException unused) {
                throw new Exception("localDbDateTimeStr '" + str2 + "' Is NOT In the UTC Format '" + DATE_TIME_FORMAT_STANDARDIZED_UTC + "'");
            }
        } catch (ParseException unused2) {
            throw new Exception("backupDbDateTimeStr '" + str + "' Is NOT In the UTC Format '" + DATE_TIME_FORMAT_STANDARDIZED_UTC + "'");
        }
    }

    public static Date dateTimeStringInUtcFormat2Date(String str) throws ParseException {
        return new SimpleDateFormat(DATE_TIME_FORMAT_STANDARDIZED_UTC).parse(str);
    }

    public static String getCurrentLocalDateTime() {
        try {
            return DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        } catch (Exception unused) {
            return null;
        }
    }

    public static String millis2LocalDateString(long j) {
        return DateFormat.getDateInstance().format(new Date(j));
    }

    public static String toUTCDateTimeString(Date date) {
        return new SimpleDateFormat(DATE_TIME_FORMAT_STANDARDIZED_UTC).format(date);
    }
}
