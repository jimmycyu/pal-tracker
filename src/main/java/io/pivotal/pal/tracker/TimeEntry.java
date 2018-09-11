package io.pivotal.pal.tracker;


import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class TimeEntry {

    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    void setId(long id){
        this.id=id;
    }

    public TimeEntry(){

    }

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id=id;
        this.projectId=projectId;
        this.userId=userId;
        this.date=date;
        this.hours=hours;
    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this.id=-1L;
        this.projectId=projectId;
        this.userId=userId;
        this.date=date;
        this.hours=hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return  this.id == timeEntry.id && projectId == timeEntry.projectId &&
                userId == timeEntry.userId &&
                hours == timeEntry.hours &&
                Objects.equals(date, timeEntry.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, userId, date, hours);
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }


}
