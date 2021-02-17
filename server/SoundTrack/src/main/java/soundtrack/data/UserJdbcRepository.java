package soundtrack.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import soundtrack.data.mappers.UserMapper;
import soundtrack.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        List<User> users = jdbcTemplate.query(sql, new UserMapper());
        users.forEach(this::attachRoles);
        return users;
    }

    @Override
    public User findById(int id) {
        final String sql = "select user_id, first_name, last_name, email, phone, access_level, password_hash from system_user where user_id = ?;";
        User user = jdbcTemplate.query(sql, new UserMapper(), id).stream().findFirst().orElse(null);
        if (user == null) {
            return null;
        }
        attachRoles(user);
        return user;
    }

    /**
     * Gets all roles for a given user and attaches them to that user
     * @param user: the user to find the roles for
     */
    private void attachRoles(User user) {
        final String sql = "select r.role_name from system_user u " +
                "inner join user_role ur on u.user_id = ur.user_id " +
                "inner join role r on r.role_id = ur.role_id " +
                "where u.user_id = ?;";
        List<String> roles = jdbcTemplate.query(sql, this::mapString, user.getUserId());
        user.setRoles(roles);
    }

    private String mapString(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString("role_name");
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
        addRoles(user.getRoles())

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    private void addRoles(List<String> roles) {
        //TODO: do this
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