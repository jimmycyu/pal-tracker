package io.pivotal.pal.tracker;


import java.sql.Time;
import java.util.List;

public interface TimeEntryRepository {

    TimeEntry create(TimeEntry timeEntry);
    TimeEntry update(long id, TimeEntry timeEntry);
    void delete(long id);
    TimeEntry get(Long id);
    TimeEntry find(Long id);
    List<TimeEntry> list();

}
