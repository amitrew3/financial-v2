package com.rew3.common.utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
    public static Matcher numberRangeMatch(String input) {
        Pattern pattern = Pattern.compile("^\\[(\\d+|\\d+\\.\\d+),(\\d+|\\d+\\.\\d+)\\]$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Rew3DateRange dateRangeMatch(String input) throws ParseException {


        Pattern pattern = Pattern.compile("\\[(\\d{4}-\\d{2}[\\-0-9:TZ'\\.\\+]*),(\\d{4}-\\d{2}[\\-0-9:TZ'\\.\\+]*)");


        Matcher matcher = pattern.matcher(input);


        String start = null;
        String end = null;
        boolean matches = false;

        if (matcher.find()) {
            start = matcher.group(1);
            end = matcher.group(2);
            matches = true;
        }
        Timestamp startDate = null;
        Timestamp endDate = null;
        if (matches) {
            startDate = Rew3Date.convertToUTC(start);
            endDate = Rew3Date.convertToUTC(end);

        }
        Rew3DateRange range = new Rew3DateRange(startDate, endDate, matches);
        return range;

    }


    public static Matcher dateFormatMatch(String input) {
        Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}-\\d{2}:\\d{2})$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher keyMatch(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z_.]+)-(lt-eq|gt-eq|lt|gt|eq|not-eq)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher startsWithMatch(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z_.]+)-(starts-with)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher endsWithMatch(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z_.]+)-(ends-with)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher containsMatch(String input) {
        Pattern pattern = Pattern.compile("(^[a-zA-Z_.]+)-(contains)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher notContainMatch(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z_.]+)(-not-contains)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher emptyMatch(String input) {
        Pattern pattern = Pattern.compile("^([a-zA-Z_.]+)-(empty)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher nonEmptyMatch(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z_.]+)-(non-empty)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher specificDateMatch(String input) {
        Pattern pattern = Pattern.compile("(last_month|last_week|yesterday|today|tomorrow|next_week|next_month|this_month|this_week)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher isNotEqualMatch(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z_.]+)-(is-not)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
    public static Matcher isEqualMatch(String input) {
        Pattern pattern = Pattern.compile("(^[a-zA-Z_.]+)-(is)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher inMatch(String input) {
        Pattern pattern = Pattern.compile("(^[a-zA-Z_.]+)-(in)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher notInMatch(String input) {
        Pattern pattern = Pattern.compile("(^[a-zA-Z_.]+)-(not-in)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
    public static Matcher dueMatch(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z_.]+)(-due)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher overdueMatch(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z_.]+)(-overdue)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public static Matcher hasValidTimeUnit(String input) {
        Pattern pattern = Pattern.compile("\\s*([0-9]+) (days|weeks|months)$");
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }


    public static boolean matchTheDotOperator(String input) {




        return input.matches("\\[a-z\\.]+");
    }
}