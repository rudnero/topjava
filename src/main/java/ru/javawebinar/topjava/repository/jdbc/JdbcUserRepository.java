package ru.javawebinar.topjava.repository.jdbc;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }
            deleteAllRoles(user.getId());
        }
        addRole(user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return setRoles(jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id));
    }

    @Override
    public User getByEmail(String email) {
        return setRoles(jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email));
    }

    @Override
    public List<User> getAll() {
        Map<Integer, Set<Role>> map = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles", rs -> {
            map.computeIfAbsent(rs.getInt("user_id"), userId -> EnumSet.noneOf(Role.class))
                    .add(Role.valueOf(rs.getString("role")));
        });

        List<User> users = jdbcTemplate.query("SELECT * FROM users u ORDER BY name, email", ROW_MAPPER);
        users.forEach(user -> user.setRoles(map.get(user.getId())));
        return users;
    }

    private void addRole(User user) {
        if (user !=null && user.getRoles() != null && !CollectionUtils.isEmpty(user.getRoles()) ) {
            String query = "INSERT INTO user_roles (role, user_id) VALUES (?, ?)";
            jdbcTemplate.batchUpdate(query, user.getRoles(), user.getRoles().size(),
                    (ps, role) -> {
                        ps.setString(1, role.name());
                        ps.setInt(2, user.getId());
                    });
        }
    }

    private void deleteAllRoles(int userId) {
        String query = "DELETE FROM user_roles WHERE user_id=?";
        jdbcTemplate.update(query, userId);
    }

    private User setRoles(List<User> users) {
        User user = DataAccessUtils.singleResult(users);
        if (user !=null) {
           List<Role> roles = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?", (rs, rowNum) -> Role.valueOf(rs.getString("role")), user.getId());
           user.setRoles(roles);
        }

        return user;
    }
}
