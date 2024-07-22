package org.example.usermanagmentsystem.service;

import org.example.usermanagmentsystem.entities.User;
import org.example.usermanagmentsystem.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "Ferid100";

    private static final String INSERT_USER_SQL = "INSERT INTO users (userName, age, createdAt, updatedAt) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM users";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }


    @Override
    public User createUser(String username, int age, LocalDate createdAt, LocalDate updatedAt) throws SQLException {
        User user = new User();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(3, username);
            preparedStatement.setInt(4,age );
            preparedStatement.setDate(5, Date.valueOf(createdAt.toString()));
            preparedStatement.setDate(6, Date.valueOf(updatedAt.toString()));
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    user.setUserName(user.getUserName());
                    user.setAge(user.getAge());
                    user.setCreatedAt(user.getCreatedAt());
                    user.setUpdatedAt(user.getUpdatedAt());
                } else {
                    throw new SQLException("Wrong number of generated keys");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Qeydiyyat zamanı xəta baş verdi");
        }
        return user;
    }


    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS_SQL)) {
            while (resultSet.next()) {
                User user = extractUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Users not found");
        }
        return users;
    }

    @Override
    public Optional<User> getUserById(int id) throws UserNotFoundException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_USER_BY_ID_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    return Optional.of(user);
                } else {
                    throw new UserNotFoundException();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("User Not found: " + id);
        }
    }

    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(id + "İstifadəçinin silinməsi uğursuz alındı");
        }
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUserName(resultSet.getString("user_name"));
        user.setAge(resultSet.getInt("age"));
        user.setCreatedAt(resultSet.getDate("created_at").toLocalDate());
        user.setUpdatedAt(resultSet.getDate("updated_at").toLocalDate());
        return user;
    }
}

