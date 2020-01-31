package org.training.food_tracker.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food_tracker.dao.impl.ConnectionFactory;
import org.training.food_tracker.model.Biometrics;
import org.training.food_tracker.model.Lifestyle;
import org.training.food_tracker.model.Sex;
import org.training.food_tracker.model.User;

import java.sql.*;
import java.util.List;

public class UserDao implements CrudDao<User> {

    public static final String CREATE_QUERY = "INSERT INTO users (username, password, full_name, "
                                                      + "national_name, email, active, role) VALUES(?,?,?,?,?,?,?)";

    public static final String READ_QUERY = "SELECT users.id, username, password, full_name, national_name, email, active, "
                                                    + "role, biometrics.id, user_id, age, norm, height, lifestyle, sex, weight FROM users JOIN "
                                                    + "biometrics ON user_id = users.id WHERE id = ?";

    public static final String FIND_BY_USERNAME_QUERY = "SELECT users.id, username, password, full_name, national_name, email, active, "
                                                            + "role, biometrics.id, user_id, age, norm, height, lifestyle, sex, weight FROM users JOIN "
                                                            + "biometrics ON user_id = users.id WHERE username = ?";

    private static final Logger log = LogManager.getLogger(UserDao.class.getName());

    @Override public User create(User user) {
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            log.debug("Prepared statement was created.");

            log.trace("Setting user's name: {}", user.getUsername());
            statement.setString(1, user.getUsername());

            statement.setString(2, user.getPassword());

            log.trace("Setting user's full name: {}", user.getFullName());
            statement.setString(3, user.getFullName());

            log.trace("Setting user's national name: {}", user.getNationalName());
            statement.setString(4, user.getNationalName());

            log.trace("Setting user's email: {}", user.getEmail());
            statement.setString(5, user.getEmail());

            log.trace("Setting user to be active : {}", user.isActive());
            statement.setBoolean(6, user.isActive());

            log.trace("Setting user's role: {}", user.getRole());
            statement.setString(7, user.getRole().toString());

            log.debug("Executing prepared statement");
            statement.executeUpdate();

            log.debug("Creating result set");
            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                log.debug("Result set was created. Setting id from DB to lecture object to return");
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            log.error("Creation of user has failed.", e);
            throw new DaoException("Creation of user has failed.", e);
        }

        log.debug("user {} was created.", user);
        return user;
    }

    @Override
    public User findById(Long id) {
        log.debug("Finding user by id:{}", id);
        User user = null;
        Biometrics biometrics = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {

            log.debug("Prepared statement was created. Setting id: {}", id);
            statement.setLong(1, id);

            log.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such user with id: " + id);
                }

                log.debug("Creating biometrics object");
                biometrics = Biometrics.builder()
                                     .id(resultSet.getLong("biometrics.id"))
                                     .age(resultSet.getBigDecimal("age"))
                                     .sex(Sex.valueOf(resultSet.getString("sex")))
                                     .weight(resultSet.getBigDecimal("weight"))
                                     .height(resultSet.getBigDecimal("height"))
                                     .lifestyle(Lifestyle.valueOf(resultSet.getString("lifestyle")))
                                     .build();

                log.debug("Creating user object");
                user = User.builder()
                               .id(id)
                               .username(resultSet.getString("username"))
                               .password(resultSet.getString("password"))
                               .fullName(resultSet.getString("full_name"))
                               .nationalName(resultSet.getString("national_name"))
                               .biometrics(biometrics)
                               .build();

                biometrics.setOwner(user);
            }
        } catch (SQLException e) {
            log.error("Finding user has failed", e);
            throw new DaoException("Finding user has failed", e);
        }

        log.debug("User {} was found by id: {}.", user.getUsername(), user.getId());
        return user;
    }

    public User findByUsername(String username) {
        log.debug("Finding user by username:{}", username);
        User user = null;
        Biometrics biometrics = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {

            log.debug("Prepared statement was created. Setting username");
            statement.setString(1, username);

            log.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such user with username: " + username);
                }

                log.debug("Creating biometrics object");
                biometrics = Biometrics.builder()
                                     .id(resultSet.getLong("biometrics.id"))
                                     .age(resultSet.getBigDecimal("age"))
                                     .sex(Sex.valueOf(resultSet.getString("sex")))
                                     .weight(resultSet.getBigDecimal("weight"))
                                     .height(resultSet.getBigDecimal("height"))
                                     .lifestyle(Lifestyle.valueOf(resultSet.getString("lifestyle")))
                                     .build();

                log.debug("Creating user object");
                user = User.builder()
                               .id(resultSet.getLong("users.id"))
                               .username(resultSet.getString("username"))
                               .password(resultSet.getString("password"))
                               .fullName(resultSet.getString("full_name"))
                               .nationalName(resultSet.getString("national_name"))
                               .biometrics(biometrics)
                               .build();

                biometrics.setOwner(user);
            }
        } catch (SQLException e) {
            log.error("Finding user has failed", e);
            throw new DaoException("Finding user has failed", e);
        }

        log.debug("User {} was found by username", user.getUsername());
        return user;
    }

    @Override public User update(User user) {
        return null;
    }

    @Override public List<User> findAll() {
        return null;
    }

    @Override public void deleteById(Long id) {

    }
}
