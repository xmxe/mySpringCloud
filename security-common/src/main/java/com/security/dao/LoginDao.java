package com.security.dao;

import com.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LoginDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUserByUsername(String username) {
        String sql = "select id, username, password from user where username = ?";
        return jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper<>(User.class));
    }

    public List<String> getPermissionsByUsername(String username) {
        String sql =
                "select d.permission\n" +
                        "from user a\n" +
                        "       join user_r_role b on a.id = b.userid\n" +
                        "       join role_r_permission c on b.roleid = c.roleid\n" +
                        "       join permission d on c.permissionid = d.id\n" +
                        "where a.username = ?\n" +
                        "union\n" +
                        "select c.permission\n" +
                        "from user a\n" +
                        "       join user_r_permission b on a.id = b.userid\n" +
                        "       join permission c on b.permissionid = c.id\n" +
                        "where a.username = ?";
        return jdbcTemplate.queryForList(sql, new String[]{username, username}, String.class);
    }

    public List<String> getRoleByUsername(String username) {
        String sql =
                "select c.role\n" +
                        "from user a\n" +
                        "       join user_r_role b on a.id = b.userid\n" +
                        "       join role c on b.roleid = c.id\n" +
                        "where a.username = ?";
        return jdbcTemplate.queryForList(sql, new String[]{username}, String.class);
    }
}
