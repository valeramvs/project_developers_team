/*
 * @(#)Specification.java   1.0 2019/11/25
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.dao;

import by.epam.outercourse.project.devteam.connect.ConnectionPool;
import by.epam.outercourse.project.devteam.entity.client.Client;
import by.epam.outercourse.project.devteam.entity.manager.Manager;
import by.epam.outercourse.project.devteam.entity.project.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SpecificationDAO extends AbstractDAO<Integer, Specification> {
    /**
     * SpecificationDAO class has got access to database's information about 'Specification'.
     * Class has got methods for CRUD-operations.
     * Class methods get SQL-queries from file 'sql_ru_RU.properties'.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(SpecificationDAO.class);

    ResourceBundle resource = ResourceBundle.getBundle("sql");

    @Override
    public int findId(Specification entity) {
        int id = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("specification.findId"));
            statement.setInt(1, entity.getIdClient());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
            entity.setId(id);
        } catch (SQLException e) {
            logger.error("Failed to find the specification by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return id;
    }

    /**
     * Method findFreeSpecs() can find the list of specifications without a manager.
     */
    public List<Specification> findFreeSpecs() {
        List<Specification> specifications = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(resource.getString("specification.findFreeSpecs"));
            while (resultSet.next()) {
                Specification specification = new Specification();
                specification.setId(resultSet.getInt("id"));
                specification.setBill(resultSet.getDouble("bill"));
                specification.setIdClient(resultSet.getInt("client_id"));
                specification.setIdManager(resultSet.getInt("manager_id"));
                specifications.add(specification);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of free specifications", e);
        } finally {
            close(statement);
            release(connection);
        }
        return specifications;
    }

    /**
     * Method findLastAppropriateId(specification) returns specification's id for correct creating
     * a new specification in database.
     */
    public int findLastAppropriateId(Specification entity) {
        int id = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("specification.findLastAppropriateId"));
            statement.setInt(1, entity.getIdClient());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
            entity.setId(id);
        } catch (SQLException e) {
            logger.error("Failed to find the specification by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return id;
    }

    public List<Specification> findSpecsByIdClient(Client entity) {
        List<Specification> specifications = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("specification.findSpecsByIdClient"));
            statement.setInt(1, entity.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Specification specification = new Specification();
                specification.setId(resultSet.getInt("id"));
                specification.setBill(resultSet.getDouble("bill"));
                specification.setIdClient(resultSet.getInt("client_id"));
                specification.setIdManager(resultSet.getInt("manager_id"));
                specifications.add(specification);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of specifications by client's ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return specifications;
    }

    public List<Specification> findSpecsByIdManager(Manager entity) {
        List<Specification> specifications = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("specification.findSpecsByIdManager"));
            statement.setInt(1, entity.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Specification specification = new Specification();
                specification.setId(resultSet.getInt("id"));
                specification.setBill(resultSet.getDouble("bill"));
                specification.setIdClient(resultSet.getInt("client_id"));
                specification.setIdManager(resultSet.getInt("manager_id"));
                specifications.add(specification);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of specifications by manager's ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return specifications;
    }

    @Override
    public List<Specification> findAll() {
        List<Specification> specifications = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(resource.getString("specification.findAll"));
            while (resultSet.next()) {
                Specification specification = new Specification();
                specification.setId(resultSet.getInt("id"));
                specification.setBill(resultSet.getDouble("bill"));
                specification.setIdClient(resultSet.getInt("client_id"));
                specification.setIdManager(resultSet.getInt("manager_id"));
                specifications.add(specification);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of specifications", e);
        } finally {
            close(statement);
            release(connection);
        }
        return specifications;
    }

    @Override
    public Specification findEntityById(Integer id) {
        Specification specification = new Specification();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("specification.findEntityById"));
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            specification.setId(resultSet.getInt("id"));
            specification.setBill(resultSet.getDouble("bill"));
            specification.setIdClient(resultSet.getInt("client_id"));
            specification.setIdManager(resultSet.getInt("manager_id"));
        } catch (SQLException e) {
            logger.error("Failed to find the spec by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return specification;
    }

    @Override
    public boolean delete(Integer id) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("specification.delete"));
            statement.setInt(1, id);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to delete the specification", e);
        } finally {
            close(statement);
            release(connection);
        }
        return flag;
    }

    @Override
    public boolean delete(Specification entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Specification create(Specification entity) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("specification.insert"));
            statement.setInt(1, entity.getIdClient());
            statement.executeUpdate();
            entity.setId(findLastAppropriateId(entity));
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to create a new specification", e);
        } finally {
            close(statement);
            release(connection);
        }
        return entity;
    }

    @Override
    public Specification update(Specification entity) {
        Specification specification = new Specification();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("specification.update"));
            statement.setDouble(1, entity.getBill());
            statement.setInt(2, entity.getIdClient());
            statement.setInt(3, entity.getIdManager());
            statement.setInt(4, entity.getId());
            statement.executeUpdate();
            specification = entity;
        } catch (SQLException e) {
            logger.error("Failed to update the specification", e);
        } finally {
            close(statement);
            release(connection);
        }
        return specification;
    }
}
