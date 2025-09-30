package es.cesguiro.data.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BaseMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    protected static Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }

    protected static BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return new BigDecimal(value);
    }

    protected static Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Double.parseDouble(value);
    }

    protected static String parseString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    protected static Long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Long.parseLong(value);
    }
}
