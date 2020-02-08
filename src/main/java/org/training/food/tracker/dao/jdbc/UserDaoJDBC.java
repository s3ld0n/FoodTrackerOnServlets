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

    public static final String FIND_BY_ID = "SELECT users.id AS u_id, username, password, first_name, last_name, email, active, "
                                                    + "role, biometrics.id, user_id, age, daily_norm_calories, height, lifestyle, sex, weight FROM users JOIN "
                                                    + "biometrics ON user_id = users.id WHERE users.id = ?";

    public static final String FIND_BY_USERNAME_QUERY = "SELECT users.id AS u_id, username, password, first_name, last_name, email, active, "
                                                            + "role, biometrics.id AS bio_id, biometrics.user_id, age, daily_norm_calories, height, lifestyle, sex, weight FROM users JOIN "
                                                            + "biometrics ON users.id = biometrics.user_id WHERE username = ?";

    public static final String FIND_ALL_QUERY = "SELECT users.id AS u_id, username, password, first_name, last_name, email, active, "
                                                  + "role, biometrics.id AS bio_id, biometrics.user_id, age, daily_norm_calories, height, lifestyle, sex, weight FROM users JOIN "
                                                  + "biometrics ON users.id = biometrics.user_id";

    private static final Logger LOG = LogManager.getLogger(UserDaoJDBC.class.getName());

    @Override public User create(User user) throws DaoException {
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            LOG.debug("Prepared statement was created.");

            setPreparedStatementParams(user, statement);

            LOG.debug("Executing prepared statement");
            statement.executeUpdate();

            setGeneratedId(user, statement);
        } catch (SQLException e) {
            LOG.error("Creation of user has failed.", e);
            throw new DaoException("Creation of user has failed.", e);
        }

        LOG.debug("user: {} was created.", user.getUsername());
        return user;
    }

    private void setGeneratedId(User user, PreparedStatement statement) throws SQLException {
        LOG.debug("Creating result set");
        try (ResultSet resultSet = statement.getGeneratedKeys()) {

            LOG.debug("Result set was created. Setting id from DB to lecture object to return");
            resultSet.next();
            user.setId(resultSet.getLong(1));
        }
    }

    private void setPreparedStatementParams(User user, PreparedStatement statement) throws SQLException {
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
    }

    @Override
    public User findById(Long id) throws DaoException {
        LOG.debug("Finding user by id:{}", id);
        User user;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {

            LOG.debug("Prepared statement was created. Setting id: {}", id);
            statement.setLong(1, id);

            user = buildUserWithBiometrics(statement);
        } catch (SQLException e) {
            LOG.error("Finding user has failed", e);
            throw new DaoException("Finding user has failed", e);
        }

        LOG.debug("User {} was found by id: {}.", user.getUsername(), user.getId());
        return user;
    }

    private User buildUserWithBiometrics(PreparedStatement statement) throws SQLException, DaoException {
        User user;
        LOG.debug("Creating result set");
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                throw new DaoException("No such user");
            }

            LOG.debug("Creating biometrics object");
            Biometrics biometrics = buildBiometrics(resultSet);

            LOG.debug("Creating user object");
            user = buildUser(resultSet);
            user.setBiometrics(biometrics);
        }
        return user;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                       .id(resultSet.getLong("u_id"))
                       .username(resultSet.getString("username"))
                       .password(resultSet.getString("password"))
                       .email(resultSet.getString("email"))
                       .firstName(resultSet.getString("first_name"))
                       .lastName(resultSet.getString("last_name"))
                       .role(Role.valueOf(resultSet.getString("role")))
                       .build();
    }

    private Biometrics buildBiometrics(ResultSet resultSet) throws SQLException {
        return Biometrics.builder()
                             .id(resultSet.getLong("bio_id"))
                             .age(resultSet.getBigDecimal("age"))
                             .sex(Sex.valueOf(resultSet.getString("sex")))
                             .weight(resultSet.getBigDecimal("weight"))
                             .height(resultSet.getBigDecimal("height"))
                             .lifestyle(Lifestyle.valueOf(resultSet.getString("lifestyle")))
                             .build();
    }

    public User findByUsername(String username) throws DaoException {
        LOG.debug("Finding user by username:{}", username);
        User user;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {

            LOG.debug("Prepared statement was created. Setting username");
            statement.setString(1, username);
            user = buildUserWithBiometrics(statement);

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

            extractAllUsers(users, statement);
        } catch (SQLException e) {
            LOG.error("Get all users has failed", e);
            throw new DaoException("Get all users has failed", e);
        }

        LOG.debug("{} users were found.", users.size());
        return users;
    }

    private void extractAllUsers(List<User> users, PreparedStatement statement) throws SQLException {
        LOG.debug("Creating result set");
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                LOG.debug("Creating biometrics object");
                Biometrics biometrics = buildBiometrics(resultSet);

                LOG.debug("Creating user object");
                User user = buildUser(resultSet);
                user.setBiometrics(biometrics);
                users.add(user);
            }
        }
    }

    @Override public User update(User user) {
        return null;
    }

    @Override public void deleteById(Long id) {

    }
}
