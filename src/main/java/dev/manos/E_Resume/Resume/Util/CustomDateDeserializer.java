package dev.manos.E_Resume.Resume.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final List<DateTimeFormatter> FORMATTERS = new ArrayList<>();
    private static final Pattern DATE_RANGE_PATTERN = Pattern.compile("(.*?)\\s*-\\s*(.*)");
    private static final Pattern MONTH_YEAR_PATTERN = Pattern.compile("(\\d{1,2})[/.-](\\d{4})");
    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("(\\d{4})[/.-](\\d{1,2})");
    private static final Pattern MONTH_NAME_YEAR_PATTERN = Pattern.compile("([a-zA-Z]+)\\s+(\\d{4})");

    static {
        // Add various date formats
        String[] patterns = {
                "yyyy-MM-dd", "dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd",
                "dd-MM-yyyy", "MM-dd-yyyy", "yyyy.MM.dd", "dd.MM.yyyy", "MM.dd.yyyy",
                "dd MMM yyyy", "MMM dd yyyy", "MMMM dd yyyy", "dd MMMM yyyy",
                "yyyy", "MMM yyyy", "MMMM yyyy"
        };
        for (String pattern : patterns) {
            FORMATTERS.add(DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
        }
        FORMATTERS.add(DateTimeFormatter.ISO_LOCAL_DATE);
        FORMATTERS.add(DateTimeFormatter.ISO_OFFSET_DATE);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        if (jsonParser.currentToken() == JsonToken.VALUE_NULL) {
            return null;
        }

        String dateStr = jsonParser.getText();

        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        dateStr = dateStr.trim();

        // Check for date range and use the start date
        Matcher rangeMatcher = DATE_RANGE_PATTERN.matcher(dateStr);
        if (rangeMatcher.matches()) {
            dateStr = rangeMatcher.group(1).trim();
        }

        // Try parsing with predefined formatters
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next formatter
            }
        }

        // Try parsing month name and year (e.g., "May 2021" or "February 2022")
        Matcher monthNameYearMatcher = MONTH_NAME_YEAR_PATTERN.matcher(dateStr);
        if (monthNameYearMatcher.matches()) {
            String monthName = monthNameYearMatcher.group(1);
            int year = Integer.parseInt(monthNameYearMatcher.group(2));
            int month = parseMonth(monthName);
            return LocalDate.of(year, month, 1);
        }

        // Try parsing month/year or year/month
        Matcher monthYearMatcher = MONTH_YEAR_PATTERN.matcher(dateStr);
        if (monthYearMatcher.matches()) {
            int month = Integer.parseInt(monthYearMatcher.group(1));
            int year = Integer.parseInt(monthYearMatcher.group(2));
            return LocalDate.of(year, month, 1);
        }

        Matcher yearMonthMatcher = YEAR_MONTH_PATTERN.matcher(dateStr);
        if (yearMonthMatcher.matches()) {
            int year = Integer.parseInt(yearMonthMatcher.group(1));
            int month = Integer.parseInt(yearMonthMatcher.group(2));
            return LocalDate.of(year, month, 1);
        }

        // Try parsing as year only
        try {
            int year = Integer.parseInt(dateStr);
            if (year >= 1900 && year <= 2100) {  // Reasonable year range
                return LocalDate.of(year, 1, 1);
            }
        } catch (NumberFormatException e) {
            // Not a valid year, continue
        }

        // If all else fails, try to extract a date using a more flexible approach
        List<Integer> numbers = extractNumbers(dateStr);
        if (numbers.size() >= 3) {
            // Assume year is the largest number
            int year = Collections.max(numbers);
            numbers.remove(Integer.valueOf(year));
            int month = numbers.get(0);
            int day = numbers.get(1);
            if (month > 12) {  // Swap if month > 12, assuming day/month order
                int temp = month;
                month = day;
                day = temp;
            }
            try {
                return LocalDate.of(year, month, day);
            } catch (DateTimeException e) {
                // Invalid date, fall through to return null
            }
        }

        // If we couldn't parse the date, return null instead of throwing an exception
        return null;
    }

    private List<Integer> extractNumbers(String str) {
        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(str);
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        return numbers;
    }

    private int parseMonth(String monthName) {
        String[] months = {"january", "february", "march", "april", "may", "june",
                "july", "august", "september", "october", "november", "december"};
        String lowercaseMonth = monthName.toLowerCase();
        for (int i = 0; i < months.length; i++) {
            if (months[i].startsWith(lowercaseMonth)) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Invalid month name: " + monthName);
    }
}