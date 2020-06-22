package ru.javawebinar.topjava.repository.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;

public class MealRowMapper implements RowMapper
{
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        LocalDateTime dateTime =
                LocalDateTime.parse(rs.getString("date_time"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String description = rs.getNString("description");
        int calories = rs.getInt("calories");
        return new Meal(id, dateTime, description, calories);
    }

}
