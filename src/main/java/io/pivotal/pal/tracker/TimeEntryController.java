package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {


    @Autowired
    TimeEntryRepository inMemoryTimeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository){
        this.inMemoryTimeEntryRepository = timeEntryRepository;
    }

    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable("timeEntryId") Long id) {

        TimeEntry result = inMemoryTimeEntryRepository.find(id);
        if(result != null){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry body) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(inMemoryTimeEntryRepository.create(body));
    }

    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity<String> delete(@PathVariable("timeEntryId") Long id) {
        inMemoryTimeEntryRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable("timeEntryId") Long id, @RequestBody TimeEntry body) {
        TimeEntry timeEntry = inMemoryTimeEntryRepository.update(id, body);
        if(timeEntry != null) {
            return ResponseEntity.ok(timeEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok(inMemoryTimeEntryRepository.list());
    }


}
