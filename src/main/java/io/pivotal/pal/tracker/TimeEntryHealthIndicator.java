package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    @Autowired
    TimeEntryRepository timeEntryRepository;

    @Override
    public Health health() {
        List<TimeEntry> existingTimeEntry = timeEntryRepository.list();
        if(existingTimeEntry == null||existingTimeEntry.size() < 5) {
            return Health.up().build();
        }
        return Health.outOfService().build();
    }
}
