package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@Repository("inMemoryTimeEntryRepository")
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> STOREAGE =  new HashMap<Long, TimeEntry>();

    private AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        if(timeEntry.getId() == -1L) {
            Long id= idGenerator.getAndIncrement();
            timeEntry.setId(id);
        }
        STOREAGE.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(STOREAGE.containsKey(id)) {
            timeEntry.setId(id);
            STOREAGE.put(id, timeEntry);
            return timeEntry;
        } else {
            return null;
        }
    }

    @Override
    public void delete(long id) {
        STOREAGE.remove(id);
    }


    @Override
    public TimeEntry get(Long id) {
        return STOREAGE.get(id);
    }

    @Override
    public TimeEntry find(Long id) {
        return STOREAGE.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
        for(TimeEntry timeEntry :STOREAGE.values() ){
            timeEntries.add(timeEntry);
        }
        return timeEntries;
    }
}
