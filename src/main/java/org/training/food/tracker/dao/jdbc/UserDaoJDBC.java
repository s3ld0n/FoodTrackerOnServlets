package org.training.food.tracker.dao.jdbc;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.UserDao;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.*;
import org.training.food.tracker.model.builder.BiometricsBuilder;
import org.training.food.tracker.model.builder.UserBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC implements UserDao {

    private static final String CREATE_QUERY = "INSERT INTO users (username, password, first_name, "
                                                      + "last_name, email, role, daily_norm_calories) VALUES(?,?,?,?,?,?,?)";

    private static final String FIND_BY_ID = "SELECT users.id AS u_id, username, password, first_name, last_name, email, "
                                                    + "role, biometrics.id, user_id, age, daily_norm_calories, height, lifestyle, sex, weight FROM users JOIN "
                                                    + "biometrics ON user_id = users.id WHERE users.id = ?";

    private static final String FIND_BY_USERNAME_QUERY = "SELECT users.id AS u_id, username, password, first_name, last_name, email, "
                                                            + "role, biometrics.id AS bio_id, biometrics.user_id, age, daily_norm_calories, height, lifestyle, sex, weight FROM users JOIN "
                                                            + "biometrics ON users.id = biometrics.user_id WHERE username = ?";

    private static final String FIND_ALL_QUERY = "SELECT users.id AS u_id, username, password, first_name, last_name, email, "
                                                  + "role, biometrics.id AS bio_id, biometrics.user_id, age, daily_norm_calories, height, lifestyle, sex, weight FROM users JOIN "
                                                  + "biometrics ON users.id = biometrics.user_id";

    private static final String UPDATE_QUERY = "UPDATE users SET username = ?, "
                                                       + "password = ?, "
                                                       + "first_name = ?, "
                                                       + "last_name = ?, "
                                                       + "email = ?, "
                                                       + "role = ?, "
                                                       + "daily_norm_calories = ? "
                                             + "WHERE id = ?";

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoJDBC.class.getName());

    @Override public User create(User user) throws DaoException {
        LOG.debug("create() :: establishing connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            LOG.debug("create() :: prepared statement was created.");

            setPreparedStatementParams(user, statement);

            LOG.debug("create() :: executing prepared statement");
            statement.executeUpdate();

            setGeneratedId(user, statement);
        } catch (SQLException e) {
            LOG.error("Creation of user has failed.", e);
            throw new DaoException("Creation of user has failed.", e);
        }

        LOG.debug("create() :: user: {} was created.", user.getUsername());
        return user;
    }

    private void setGeneratedId(User user, PreparedStatement statement) throws SQLException {
        LOG.debug("setGeneratedId() :: creating result set");
        try (ResultSet resultSet = statement.getGeneratedKeys()) {

            LOG.debug("setGeneratedId() :: result set was created. Setting id from DB to user object to return");
            resultSet.next();
            user.setId(resultSet.getLong(1));
        }
    }

    private void setPreparedStatementParams(User user, PreparedStatement statement) throws SQLException {
        LOG.trace("setPreparedStatementParams() :: setting user's name: {}", user.getUsername());
        statement.setString(1, user.getUsername());

        statement.setString(2, user.getPassword());

        LOG.trace("setPreparedStatementParams() :: setting user's first name: {}", user.getFirstName());
        statement.setString(3, user.getFirstName());

        LOG.trace("setPreparedStatementParams() :: setting user's last name: {}", user.getLastName());
        statement.setString(4, user.getLastName());

        LOG.trace("setPreparedStatementParams() :: setting user's email: {}", user.getEmail());
        statement.setString(5, user.getEmail());

        LOG.trace("setPreparedStatementParams() :: setting user's role: ");
        statement.setString(6, Role.USER.toString());

        LOG.trace("setPreparedStatementParams() :: setting user's daily norm calories: {}", user.getDailyNormCalories());
        statement.setBigDecimal(7, user.getDailyNormCalories());
    }

    @Override
    public User findById(Long id) throws DaoException {
        LOG.debug("Finding user by id:{}", id);
        User user;
        LOG.debug("findById() :: establishing connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {

            LOG.debug("findById() :: prepared statement was created. Setting id: {}", id);
            statement.setLong(1, id);

            user = buildUserWithBiometrics(statement);
        } catch (SQLException e) {
            LOG.error("Finding user has failed", e);
            throw new DaoException("Finding user has failed", e);
        }

        LOG.debug("findById() :: user {} was found by id: {}.", user.getUsername(), user.getId());
        return user;
    }

    private User buildUserWithBiometrics(PreparedStatement statement) throws SQLException, DaoException {
        User user;
        LOG.debug("buildUserWithBiometrics() :: creating result set");
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                throw new DaoException("No such user");
            }

            LOG.debug("buildUserWithBiometrics() :: creating biometrics object");
            Biometrics biometrics = buildBiometrics(resultSet);

            LOG.debug("buildUserWithBiometrics() :: creating user object");
            user = buildUser(resultSet);
            user.setBiometrics(biometrics);
        }
        return user;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return UserBuilder.instance()
                       .id(resultSet.getLong("u_id"))
                       .username(resultSet.getString("username"))
                       .password(resultSet.getString("password"))
                       .email(resultSet.getString("email"))
                       .firstName(resultSet.getString("first_name"))
                       .lastName(resultSet.getString("last_name"))
                       .dailyNormCalories(resultSet.getBigDecimal("daily_norm_calories"))
                       .role(Role.valueOf(resultSet.getString("role")))
                       .build();
    }

    private Biometrics buildBiometrics(ResultSet resultSet) throws SQLException {
        return BiometricsBuilder.instance()
                             .id(resultSet.getLong("bio_id"))
                             .age(resultSet.getBigDecimal("age"))
                             .sex(Sex.valueOf(resultSet.getString("sex")))
                             .weight(resultSet.getBigDecimal("weight"))
                             .height(resultSet.getBigDecimal("height"))
                             .lifestyle(Lifestyle.valueOf(resultSet.getString("lifestyle")))
                             .build();
    }

    public User findByUsername(String username) throws DaoException {
        LOG.debug("Finding user by username: {}", username);
        User user;

        LOG.debug("findByUsername() :: establishing connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {

            LOG.debug("findByUsername() :: prepared statement was created. Setting username");
            statement.setString(1, username);
            user = buildUserWithBiometrics(statement);

        } catch (SQLException e) {
            LOG.error("Finding user has failed", e);
            throw new DaoException("Finding user has failed", e);
        }

        LOG.debug("findByUsername() :: user {} was found by username", user.getUsername());
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {
        LOG.debug("Finding all users");
        List<User> users = new ArrayList<>();

        LOG.debug("findAll() :: establishing connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {

            LOG.debug("findAll() :: extracting users from resultSet");
            extractAllUsers(users, statement);
        } catch (SQLException e) {
            LOG.error("Finding all users failed", e);
            throw new DaoException("Finding all users failed", e);
        }

        LOG.debug("findAll() :: {} users were found.", users.size());
        return users;
    }

    private void extractAllUsers(List<User> users, PreparedStatement statement) throws SQLException {
        LOG.debug("extractAllUsers() :: creating result set");
        try (ResultSet resultSet = statement.executeQuery()) {

            LOG.debug("extractAllUsers() :: iterating through result set");
            while (resultSet.next()) {
                LOG.trace("extractAllUsers() :: creating biometrics object");
                Biometrics biometrics = buildBiometrics(resultSet);

                LOG.trace("extractAllUsers() :: creating user object");
                User user = buildUser(resultSet);
                user.setBiometrics(biometrics);
                users.add(user);
            }
        }
    }

    @Override public User update(User user) throws DaoException {
        LOG.debug("update() :: updating user: {}", user.getUsername());

        LOG.debug("findById() :: establishing connection, preparing statement");
        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            LOG.debug("findById() :: setting parameters of statement");
            setPreparedStatementParams(user, statement);
            statement.setLong(8, user.getId());

            LOG.debug("findById() :: executing update");
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("update() :: update of user failed!", e);
            throw new DaoException("update of user failed!", e);
        }
        LOG.debug("findById() :: user {} was successfully updated", user.getUsername());
        return user;
    }

    @Override public void deleteById(Long id) {

    }
}
