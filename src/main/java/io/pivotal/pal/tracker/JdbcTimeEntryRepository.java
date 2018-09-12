package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;


public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update( con ->  {
                PreparedStatement preparedStatement =
                        con.prepareStatement("INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)", new String[]{"id"});
                preparedStatement.setLong(1, timeEntry.getProjectId());
                preparedStatement.setLong(2, timeEntry.getUserId());
                preparedStatement.setDate(3, Date.valueOf(timeEntry.getDate()));
                preparedStatement.setInt(4, timeEntry.getHours());
                return preparedStatement;
            }, generatedKeyHolder);
        timeEntry.setId(generatedKeyHolder.getKey().longValue());
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long id) {
    try{
        return jdbcTemplate.queryForObject(
                "select id, project_id, user_id, date, hours from time_entries where id=?", new Object[]{id}, new RowMapper<TimeEntry>() {

                    @Override
                    public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {

                        TimeEntry timeEntry = new TimeEntry(rs.getLong(1), rs.getLong(2), rs.getLong(3), LocalDate.parse(rs.getDate(4).toString()), rs.getInt(5));
                        return timeEntry;
                    }
                });


     } catch (EmptyResultDataAccessException erdae){
        return null;
    }
    }

    @Override
    public List<TimeEntry> list() {

        return jdbcTemplate.query("select id, project_id, user_id, date, hours from time_entries", new RowMapper<TimeEntry>() {

            @Override
            public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {

                TimeEntry timeEntry = new TimeEntry(rs.getLong(1), rs.getLong(2), rs.getLong(3), LocalDate.parse(rs.getDate(4).toString()), rs.getInt(5));
                return timeEntry;
            }
        });
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        jdbcTemplate.update( con -> {
            PreparedStatement preparedStatement = con.prepareStatement("update time_entries set project_id=?, user_id=?, date=?, hours=? where id=?");
            preparedStatement.setLong(1, timeEntry.getProjectId());
            preparedStatement.setLong(2, timeEntry.getUserId());
            preparedStatement.setDate(3, Date.valueOf(timeEntry.getDate()));
            preparedStatement.setInt(4, timeEntry.getHours());
            preparedStatement.setLong(5, id);
            return preparedStatement;
        });
        timeEntry.setId(id);
        return timeEntry;

    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("delete from time_entries where id=?");
            preparedStatement.setLong(1, id);
            return preparedStatement;
        });
    }
}
