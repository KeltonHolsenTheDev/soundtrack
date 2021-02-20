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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public User findByEmail(String email) {
        final String sql = "select user_id, first_name, last_name, email, phone, access_level, password_hash from system_user where email = ?;";
        User user = jdbcTemplate.query(sql, new UserMapper(), email).stream().findFirst().orElse(null);
        if (user == null) {
            return null;
        }
        attachRoles(user);
        return user;
    }

    //this is used by EventService
    @Override
    public Map<User, String> findUserEventRoles(int userId, int eventId) {
        User user = this.findById(userId);
        final String sql = "select r.role_name from role r inner join user_role ur on r.role_id = ur.role_id " +
                "inner join event_user_role er on er.user_role_id = ur.user_role_id " +
                "where er.event_id = ?;";
        List<String> roles = jdbcTemplate.query(sql, this::mapRoleName, eventId);
        HashMap<User, String> roleMap = new HashMap<>();
        for (String role: roles) {
            roleMap.put(user, role);
        }
        return roleMap;
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
        List<String> roles = jdbcTemplate.query(sql, this::mapRoleName, user.getUserId());
        user.setRoles(roles);
    }

    private String mapRoleName(ResultSet resultSet, int i) throws SQLException {
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
        else {
            addRoles(user.getRoles(), keyHolder.getKey().intValue());
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    private void addRoles(List<String> roles, int userId) {
        for (String role: roles) {
            //Check if role is already present
            String sql = "select role_id from role where role_name = ?;";
            int roleId = jdbcTemplate.query(sql, this::mapRoleId, role).stream().findFirst().orElse(-1);

            //If it isn't, add it
            if (roleId == -1) {
                sql = "insert into role (role_name) values (?);";
                jdbcTemplate.update(sql, role);
                //Get the id of the role
                sql = "select role_id from role where role_name = ?;";
                roleId = jdbcTemplate.query(sql, this::mapRoleId, role).stream().findFirst().orElse(-1);
            }
            if (roleId == -1) {
                //give up. print since this is clearly an internal error
                System.out.println("Role " + role + " could not be added to user " + userId);
            }
            else {
                //Add the user and role relationship to the bridge table
                sql = "insert into user_role (user_id, role_id) values (?, ?);";
                jdbcTemplate.update(sql, userId, roleId);
            }

        }
    }

    private int mapRoleId(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt("role_id");
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
        boolean success = jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getAccessLevel().name(), user.getPassword(), user.getUserId()) > 0;
        if (success) {
            updateRoles(user.getRoles(), user.getUserId());
        }
        return success;
    }

    private void updateRoles(List<String> roles, int userId) {
        //Delete the user's old roles
        jdbcTemplate.update("delete from user_role where user_id = ?;", userId);
        //Add their new roles
        addRoles(roles, userId);
    }

    @Override
    public boolean deleteById(int userId) {
        jdbcTemplate.update("delete from user_role where user_id = ?;", userId); //delete the user's roles
        return jdbcTemplate.update("delete from system_user where user_id = ?;", userId) > 0;
    }
}
