package org.training.food.tracker.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.BiometricsDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.*;
import org.training.food.tracker.model.builder.BiometricsBuilder;
import org.training.food.tracker.model.builder.UserBuilder;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BiometricsDaoJDBC implements BiometricsDao {

    private static final String CREATE_QUERY = "INSERT INTO biometrics (user_id, age, height, weight, lifestyle, sex) "
                                                 + "VALUES (?,?,?,?,?,?)";

    private static final String FIND_BY_USER_ID = "SELECT biometrics.id AS biometrics_id, "
                                                          + "biometrics.age AS biometrics_age, "
                                                          + "biometrics.height AS biometrics_height, "
                                                          + "biometrics.weight AS biometrics_weight, "
                                                          + "biometrics.lifestyle AS biometrics_lifestyle, "
                                                          + "biometrics.sex AS biometrics_sex, "
                                                          + "biometrics.user_id AS biometrics_user_id "
                                                + "FROM biometrics "
                                                + "WHERE biometrics.user_id = ?";

    private static final String FIND_ALL_QUERY = "SELECT biometrics.id AS biometrics_id, "
                                                      + "biometrics.age AS biometrics_age, "
                                                      + "biometrics.height AS biometrics_height, "
                                                      + "biometrics.weight AS biometrics_weight, "
                                                      + "biometrics.lifestyle AS biometrics_lifestyle, "
                                                      + "biometrics.sex AS biometrics_sex, "
                                                      + "biometrics.user_id AS biometrics_user_id, "
                                                      + "users.id AS users_id, "
                                                      + "users.username AS users_username, "
                                                      + "users.password AS users_password, "
                                                      + "users.email AS users_email, "
                                                      + "users.first_name AS users_first_name, "
                                                      + "users.last_name AS users_last_name, "
                                                      + "users.role AS users_role, "
                                                      + "users.daily_norm_calories AS users_daily_norm_calories "
                                             + "FROM biometrics JOIN users ON biometrics.user_id = users.id";

    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE biometrics.id = ?";

    private static final String UPDATE_QUERY = "UPDATE biometrics"
                                                       + " SET age = ?, "
                                                       + "height = ?, "
                                                       + "weight = ?, "
                                                       + "lifestyle = ?, "
                                                       + "sex = ? "
                                               + "WHERE user_id = ?";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM biometrics WHERE id = ?";

    private static final Logger LOG = LoggerFactory.getLogger(BiometricsDaoJDBC.class.getName());

    @Override public Biometrics create(Biometrics biometrics) throws DaoException {
        LOG.debug("create()");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            LOG.debug("create() :: prepared statement created");

            setPreparedStatement(biometrics, statement);
            LOG.debug("create() :: executing statement");
            statement.executeUpdate();

            setGeneratedId(biometrics, statement);
        } catch (SQLException e) {
            LOG.error("biometrics creation failed");
            throw new DaoException("biometrics creation failed", e);
        }
        return biometrics;
    }

    private void setGeneratedId(Biometrics biometrics, PreparedStatement statement) throws SQLException {
        LOG.debug("setGeneratedId()");
        try (ResultSet resultSet = statement.getGeneratedKeys()) {

            LOG.debug("Result set was created. Setting id from DB to biometrics object to return");
            resultSet.next();
            biometrics.setId(resultSet.getLong(1));
        }
    }

    private void setPreparedStatement(Biometrics biometrics, PreparedStatement statement) throws SQLException {
        LOG.debug("setPreparedStatement()");
        statement.setLong(1, biometrics.getOwner().getId());
        statement.setBigDecimal(2, biometrics.getAge());
        statement.setBigDecimal(3, biometrics.getHeight());
        statement.setBigDecimal(4, biometrics.getWeight());
        statement.setString(5, biometrics.getLifestyle().toString());
        statement.setString(6, biometrics.getSex().toString());
    }

    @Override public Biometrics findById(Long id) throws DaoException {
        LOG.debug("findById() :: finding biometrics by id: {}", id);
        Biometrics biometrics;
        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                LOG.debug("findById() :: building biometrics and user from result set");
                biometrics = buildBiometrics(resultSet);
                User owner = buildUser(resultSet);

                biometrics.setOwner(owner);
                owner.setBiometrics(biometrics);
            }
        } catch (SQLException e) {
            LOG.error("findById() :: error occurred", e);
            throw new DaoException("findById() :: error occurred", e);
        }
        return biometrics;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return UserBuilder.instance()
                       .id(resultSet.getLong("users_id"))
                       .username(resultSet.getString("users_username"))
                       .password(resultSet.getString("users_password"))
                       .email(resultSet.getString("users_email"))
                       .firstName(resultSet.getString("users_first_name"))
                       .lastName(resultSet.getString("users_last_name"))
                       .role(Role.valueOf(resultSet.getString("users_role")))
                       .dailyNormCalories(resultSet.getBigDecimal("users_daily_norm_calories"))
                       .build();
    }

    private Biometrics buildBiometrics(ResultSet resultSet) throws SQLException {
        return BiometricsBuilder.instance()
                       .id(resultSet.getLong("biometrics_id"))
                       .sex(Sex.valueOf(resultSet.getString("biometrics_sex")))
                       .age(resultSet.getBigDecimal("biometrics_age"))
                       .height(resultSet.getBigDecimal("biometrics_height"))
                       .weight(resultSet.getBigDecimal("biometrics_weight"))
                       .lifestyle(Lifestyle.valueOf(resultSet.getString("biometrics_lifestyle")))
                       .build();
    }

    public Biometrics findByOwner(User user) throws DaoException {
        Biometrics biometrics;
        LOG.debug("findByOwner() :: finding biometrics by owner");
        LOG.debug("findByOwner() :: establishing connection");
        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID)) {

            LOG.debug("findByOwner() :: setting user id to prepared statement");
            statement.setLong(1, user.getId());

            LOG.debug("findByOwner() :: executing query");
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                LOG.debug("findByOwner() :: extracting biometrics from result set");
                biometrics = buildBiometrics(resultSet);
                biometrics.setOwner(user);
            }
        } catch (SQLException e) {
            LOG.error("findByOwner() :: error occurred while finding biometrics", e);
            throw new DaoException("error occurred while finding biometrics", e);
        }
        LOG.debug("findByOwner() :: biometrics was successfully found");
        return biometrics;
    }

    @Override public List<Biometrics> findAll() throws DaoException {
        LOG.debug("findAll() :: finding all biometrics");

        List<Biometrics> biometricsList = new ArrayList<>();
        LOG.debug("findAll() :: establishing connection, making prepared statement");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {

            LOG.debug("findAll() :: extracting biometrics and users from result set");
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    User owner = buildUser(resultSet);
                    Biometrics biometrics = buildBiometrics(resultSet);

                    owner.setBiometrics(biometrics);
                    biometrics.setOwner(owner);

                    biometricsList.add(biometrics);
                }
            }

        } catch (SQLException e) {
            LOG.error("findAll() :: error occurred", e);
            throw new DaoException("findAll() :: error occurred", e);
        }

        LOG.debug("{} biometrics objects were successfully found", biometricsList.size());
        return biometricsList;
    }

    @Override public Biometrics update(Biometrics biometrics) throws DaoException{
        LOG.debug("update() :: updating biometrics");

        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            setPreparedStatementParams(biometrics, statement);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("update() :: error occurred during biometrics update", e);
            throw new DaoException("error occurred during biometrics update", e);
        }
        return biometrics;
    }

    private void setPreparedStatementParams(Biometrics biometrics, PreparedStatement statement) throws SQLException {
        statement.setBigDecimal(1, biometrics.getAge());
        statement.setBigDecimal(2, biometrics.getHeight());
        statement.setBigDecimal(3, biometrics.getWeight());
        statement.setString(4, biometrics.getLifestyle().toString());
        statement.setString(5, biometrics.getSex().toString());
        statement.setLong(6, biometrics.getOwner().getId());
    }

    @Override public void deleteById(Long id) throws DaoException {
        LOG.debug("deleteById()");
        int affectedRows = 0;
        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)){

            LOG.debug("deleteById() :: setting id and executing statement");
            statement.setLong(1, id);
            affectedRows = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("deleteById() :: error occurred", e);
            throw new DaoException("deleteById() :: error occurred", e);
        }

        if (affectedRows != 0) {
            LOG.debug("deleteById() :: {} rows were successfully deleted", affectedRows);
        } else {
            LOG.debug("deleteById() :: 0 rows were deleted. Must be no such id: {}", id);
        }
    }
}
