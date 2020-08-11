package com.rew3.common.utils;

import java.sql.Timestamp;

public class Rew3DateRange {

    Timestamp startDate;
    Timestamp endDate;
    boolean matches=false;

    public Rew3DateRange(Timestamp startDate, Timestamp endDate, boolean matches) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.matches = matches;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public boolean isMatches() {
        return matches;
    }

    public void setMatches(boolean matches) {
        this.matches = matches;
    }


    public Rew3DateRange(Timestamp startDate, Timestamp endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
