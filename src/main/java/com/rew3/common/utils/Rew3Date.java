package com.rew3.common.utils;

import com.rew3.common.model.Flags;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Rew3Date {

    public static Timestamp convertToUTC(String value) {

        List<DateTimeParser> parsers = Arrays.asList(DateTimeFormat.forPattern(Rew3DateFormat.YEAR_MONTH).getParser(),
                DateTimeFormat.forPattern(Rew3DateFormat.YEAR_MONTH_DATE).getParser(),
                DateTimeFormat.forPattern(Rew3DateFormat.YEAR_MONTH_DATE_TIME).getParser(),
                DateTimeFormat.forPattern(Rew3DateFormat.YEAR_MONTH_DATE_TIME_SINGLE).getParser(),
                DateTimeFormat.forPattern(Rew3DateFormat.YEAR_MONTH_DATE_TIME_WITH_TIMEZONE).getParser());
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(null, (DateTimeParser[]) parsers.toArray()).toFormatter();

        org.joda.time.DateTime dateTime = formatter.parseDateTime(value);
        org.joda.time.DateTime utc = dateTime.toDateTime(DateTimeZone.UTC);

        Timestamp timeStamp = new Timestamp(utc.getMillis());

        return timeStamp;
    }


    public static Rew3DateRange getTimestamp(Flags.SpecificDateType type) {
        Timestamp start = null;
        Timestamp end = null;


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //int week = cal.get(Calendar.WEEK_OF_YEAR);

        if (type.equals(Flags.SpecificDateType.THIS_WEEK)) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            setTheStart(cal);
            // start = cal.getTime();//Date of Monday of current week
            start = new Timestamp(cal.getTimeInMillis());

            cal.add(Calendar.DATE, 6);//Add 6 days to get Sunday of next week
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            setTheEnd(cal);
            end = new Timestamp(cal.getTimeInMillis());

        } else if (type.equals(Flags.SpecificDateType.LAST_WEEK)) {

            int week = cal.get(Calendar.WEEK_OF_YEAR);
            System.out.println(week);
            cal.set(Calendar.WEEK_OF_YEAR, week - 1);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            setTheStart(cal);
            start = new Timestamp(cal.getTimeInMillis());

            cal.add(Calendar.DATE, 6);//Add 6 days to get Sunday of next week
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            setTheEnd(cal);
            end = new Timestamp(cal.getTimeInMillis());

        } else if (type.equals(Flags.SpecificDateType.NEXT_WEEK)) {

            int week = cal.get(Calendar.WEEK_OF_YEAR);
            System.out.println(week);
            cal.set(Calendar.WEEK_OF_YEAR, week + 1);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            setTheStart(cal);
            start = new Timestamp(cal.getTimeInMillis());

            cal.add(Calendar.DATE, 6);//Add 6 days to get Sunday of next week
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            setTheEnd(cal);
            end = new Timestamp(cal.getTimeInMillis());

        } else if (type.equals(Flags.SpecificDateType.THIS_MONTH)) {

            int month = cal.get(Calendar.MONTH);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH,1);

            setTheStart(cal);
            start = new Timestamp(cal.getTimeInMillis());


            cal.add(Calendar.MONTH, 1);
            //setTheEnd(cal);
            end = new Timestamp(cal.getTimeInMillis()-1);

        }
        else if (type.equals(Flags.SpecificDateType.LAST_MONTH)) {

            int month = cal.get(Calendar.MONTH);
            cal.set(Calendar.MONTH, month-1);
            cal.set(Calendar.DAY_OF_MONTH,1);
            setTheStart(cal);
            start = new Timestamp(cal.getTimeInMillis());

            cal.set(Calendar.MONTH, month);
            end = new Timestamp(cal.getTimeInMillis()-1);

        }
        else if (type.equals(Flags.SpecificDateType.NEXT_MONTH)) {

            int month = cal.get(Calendar.MONTH);
            cal.set(Calendar.MONTH, month+1);
            cal.set(Calendar.DAY_OF_MONTH,1);
            setTheStart(cal);
            start = new Timestamp(cal.getTimeInMillis());

            cal.set(Calendar.MONTH, month+2);
            end = new Timestamp(cal.getTimeInMillis()-1);

        }
        else if (type.equals(Flags.SpecificDateType.TODAY)) {
            setTheStart(cal);
            start = new Timestamp(cal.getTimeInMillis());

            setTheEnd(cal);
            end = new Timestamp(cal.getTimeInMillis());

        }
        else if (type.equals(Flags.SpecificDateType.YESTERDAY)) {

            int day = cal.get(Calendar.DAY_OF_YEAR);
            cal.set(Calendar.DAY_OF_YEAR, day-1);
            setTheStart(cal);
            start = new Timestamp(cal.getTimeInMillis());

           setTheEnd(cal);
            end = new Timestamp(cal.getTimeInMillis());

        }

        else if (type.equals(Flags.SpecificDateType.TOMORROW)) {

            int day = cal.get(Calendar.DAY_OF_YEAR);
            cal.set(Calendar.DAY_OF_YEAR, day+1);
            setTheStart(cal);
            start = new Timestamp(cal.getTimeInMillis());

           setTheEnd(cal);
            end = new Timestamp(cal.getTimeInMillis());

        }


        return new Rew3DateRange(start, end);
    }

    private static void setTheEnd(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
    }

    private static void setTheStart(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }


}
