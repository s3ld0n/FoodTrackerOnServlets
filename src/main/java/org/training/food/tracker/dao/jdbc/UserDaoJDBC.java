package org.training.food.tracker.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.UserDao;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC implements UserDao {

    public static final String CREATE_QUERY = "INSERT INTO users (username, password, first_name, "
                                                      + "last_name, email, active, role) VALUES(?,?,?,?,?,?,?)";

    public static final String READ_QUERY = "SELECT users.id AS u_id, username, password, first_name, last_name, email, active, "
                                                    + "role, biometrics.id, user_id, age, norm, height, lifestyle, sex, weight FROM users JOIN "
                                                    + "biometrics ON user_id = users.id WHERE users.id = ?";

    public static final String FIND_BY_USERNAME_QUERY = "SELECT users.id AS u_id, username, password, first_name, last_name, email, active, "
                                                            + "role, biometrics.id AS bio_id, biometrics.user_id, age, norm, height, lifestyle, sex, weight FROM users JOIN "
                                                            + "biometrics ON users.id = biometrics.user_id WHERE username = ?";

    public static final String FIND_ALL_QUERY = "SELECT users.id AS u_id, username, password, first_name, last_name, email, active, "
                                                  + "role, biometrics.id AS bio_id, biometrics.user_id, age, norm, height, lifestyle, sex, weight FROM users JOIN "
                                                  + "biometrics ON users.id = biometrics.user_id";

    private static final Logger LOG = LogManager.getLogger(UserDaoJDBC.class.getName());

    @Override public User create(User user) throws DaoException {
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            LOG.debug("Prepared statement was created.");

            LOG.trace("Setting user's name: {}", user.getUsername());
            statement.setString(1, user.getUsername());

            statement.setString(2, user.getPassword());

            LOG.trace("Setting user's first name: {}", user.getFirstName());
            statement.setString(3, user.getFirstName());

            LOG.trace("Setting user's last name: {}", user.getLastName());
            statement.setString(4, user.getLastName());

            LOG.trace("Setting user's email: {}", user.getEmail());
            statement.setString(5, user.getEmail());

            LOG.trace("Setting user to be active : {}", user.isActive());
            statement.setBoolean(6, user.isActive());

            LOG.trace("Setting user's role: {}", user.getRole());
            statement.setString(7, user.getRole().toString());

            LOG.debug("Executing prepared statement");
            statement.executeUpdate();

            LOG.debug("Creating result set");
            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                LOG.debug("Result set was created. Setting id from DB to lecture object to return");
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOG.error("Creation of user has failed.", e);
            throw new DaoException("Creation of user has failed.", e);
        }

        LOG.debug("user {} was created.", user);
        return user;
    }

    @Override
    public User findById(Long id) throws DaoException {
        LOG.debug("Finding user by id:{}", id);
        User user = null;
        Biometrics biometrics = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {

            LOG.debug("Prepared statement was created. Setting id: {}", id);
            statement.setLong(1, id);

            LOG.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new DaoException("No such user with id: " + id);
                }

                user = extractUser(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Finding user has failed", e);
            throw new DaoException("Finding user has failed", e);
        }

        LOG.debug("User {} was found by id: {}.", user.getUsername(), user.getId());
        return user;
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        Biometrics biometrics;
        User user;
        LOG.debug("Creating biometrics object");
        biometrics = Biometrics.builder()
                             .id(resultSet.getLong("bio_id"))
                             .age(resultSet.getBigDecimal("age"))
                             .sex(Sex.valueOf(resultSet.getString("sex")))
                             .weight(resultSet.getBigDecimal("weight"))
                             .height(resultSet.getBigDecimal("height"))
                             .lifestyle(Lifestyle.valueOf(resultSet.getString("lifestyle")))
                             .build();

        LOG.debug("Creating user object");
        user = User.builder()
                       .id(resultSet.getLong("u_id"))
                       .username(resultSet.getString("username"))
                       .password(resultSet.getString("password"))
                       .email(resultSet.getString("email"))
                       .firstName(resultSet.getString("first_name"))
                       .lastName(resultSet.getString("last_name"))
                       .role(Role.valueOf(resultSet.getString("role")))
                       .biometrics(biometrics)
                       .build();
        return user;
    }

    public User findByUsername(String username) throws DaoException {
        LOG.debug("Finding user by username:{}", username);
        User user;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {

            LOG.debug("Prepared statement was created. Setting username");
            statement.setString(1, username);

            LOG.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such user with username: " + username);
                }

                user = extractUser(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Finding user has failed", e);
            throw new DaoException("Finding user has failed", e);
        }

        LOG.debug("User {} was found by username", user.getUsername());
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {
        LOG.debug("Finding all users");
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {

            LOG.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(extractUser(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error("Get all users has failed", e);
            throw new DaoException("Get all users has failed", e);
        }

        LOG.debug("{} users were found.", users.size());
        return users;
    }

    @Override public User update(User user) {
        return null;
    }

    @Override public void deleteById(Long id) {

    }
}
