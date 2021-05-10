package ru.databases.client.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class Formatter {
    private static final DecimalFormat dfGerman = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.GERMAN));

    private Formatter() {}

    public static String convertToString(Double value) {
        if (value != null) {
            return dfGerman.format(value);
        } else {
            return "";
        }
    }

    public static String convertToString(Integer value) {
        if (value != null) {
            return String.valueOf(value);
        } else {
            return "";
        }
    }

    public static String convertToString(Long value) {
        if (value != null) {
            return String.valueOf(value);
        } else {
            return "";
        }
    }

    public static Double convertToDouble(String value) {
        if (!value.isEmpty()) {
            Double res = null;
            try {
                res = dfGerman.parse(value).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return res;
        } else {
            return null;
        }
    }

    public static Integer convertToInteger(String value) {
        if (!value.isEmpty()) {
            return Integer.valueOf(value);
        } else {
            return null;
        }
    }

    public static Long convertToLong(String value) {
        if (!value.isEmpty()) {
            return Long.valueOf(value);
        } else {
            return null;
        }
    }

    public static String checkStringIsEmpty(String value) {
        if (!value.isEmpty()) {
            return value;
        } else {
            return null;
        }
    }

    public static String checkStringIsNull(String value) {
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }
}
