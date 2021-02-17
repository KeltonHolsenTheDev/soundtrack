package soundtrack.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import soundtrack.models.AccessLevel;
import soundtrack.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        user.setAccessLevel(AccessLevel.valueOf(resultSet.getString("access_level")));
        user.setPassword(resultSet.getString("password_hash"));
        return user;
    }
}
