package com.rew3.sale.recurringinvoice.model;

import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = DB.Table.RECURRING_SCHEDULE)
public class RecurringSchedule extends AbstractEntity {

    @NotNull(message = "Title must not be null")
    @Column(name = DB.Field.RecurringSchedule.TITLE)
    private String title;

    @Column(name = DB.Field.RecurringSchedule.SCHEDULE_TYPE)
    private String scheduleType;

    @Column(name = DB.Field.RecurringSchedule.COUNT)
    private int count;

    @Column(name = DB.Field.RecurringSchedule.DAY_INDEX)
    private int dayIndex;

    @Column(name = DB.Field.RecurringSchedule.MONTH_INDEX)
    private int monthIndex;


    @Column(name = DB.Field.RecurringSchedule.WEEK_DAY_INDEX)
    private String weekDayIndex;

    @Column(name = DB.Field.RecurringSchedule.DESCRIPTION)
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public Integer getMonthIndex() {
        return monthIndex;
    }

    public void setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public String getWeekDayIndex() {
        return weekDayIndex;
    }

    public void setWeekDayIndex(String weekDayIndex) {
        this.weekDayIndex = weekDayIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
