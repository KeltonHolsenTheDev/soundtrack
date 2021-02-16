package soundtrack.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import soundtrack.data.mappers.UserMapper;
import soundtrack.models.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<User> findAll() {
        final String sql = "select user_id, first_name, last_name, email, phone, access_level, password_hash from system_user;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User findById(int id) {
        final String sql = "select user_id, first_name, last_name, email, phone, access_level, password_hash from system_user where user_id = ?;";
        return jdbcTemplate.query(sql, new UserMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public User addUser(User user) {
        final String sql = "insert into system_user (first_name, last_name, email, phone, access_level, password_hash) values " +
                "(?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAccessLevel().name());
            ps.setString(6, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "update system_user set " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "access_level = ?, " +
                "password_hash = ? " +
                "where user_id = ?;";
        return jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getAccessLevel().name(), user.getPassword(), user.getUserId()) > 0;
    }

    @Override
    public boolean deleteById(int userId) {
        return jdbcTemplate.update("delete from system_user where user_id = ?;", userId) > 0;
    }
}
