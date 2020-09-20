package com.rew3.sale.recurringinvoice.model;

import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Table(name = DB.Table.RECURRING_SCHEDULE)
public class RecurringSchedule extends AbstractEntity {

    @Column(name = DB.Field.RecurringSchedule.TITLE)
    private String title;

    @Column(name = DB.Field.RecurringSchedule.SCHEDULE_TYPE)
    private Timestamp scheduleType;

    @Column(name = DB.Field.RecurringSchedule.DAY_INDEX)
    private Timestamp dayIndex;

    @Column(name = DB.Field.RecurringSchedule.MONTH_INDEX)
    private String monthIndex;

    @Column(name = DB.Field.RecurringSchedule.WEEK_INDEX)
    private String weekIndex;

    @Column(name = DB.Field.RecurringSchedule.WEEK_DAY_INDEX)
    private String weekDayIndex;

    @Column(name = DB.Field.RecurringSchedule.YEAR_INDEX)
    private String yearIndex;

    @Column(name = DB.Field.RecurringSchedule.DESCRIPTION)
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Timestamp scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Timestamp getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(Timestamp dayIndex) {
        this.dayIndex = dayIndex;
    }

    public String getMonthIndex() {
        return monthIndex;
    }

    public void setMonthIndex(String monthIndex) {
        this.monthIndex = monthIndex;
    }

    public String getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(String weekIndex) {
        this.weekIndex = weekIndex;
    }

    public String getWeekDayIndex() {
        return weekDayIndex;
    }

    public void setWeekDayIndex(String weekDayIndex) {
        this.weekDayIndex = weekDayIndex;
    }

    public String getYearIndex() {
        return yearIndex;
    }

    public void setYearIndex(String yearIndex) {
        this.yearIndex = yearIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
