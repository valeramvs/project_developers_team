/*
 * @(#)ManagerDAO.java   1.0 2019/11/25
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.dao;

import by.epam.outercourse.project.devteam.connect.ConnectionPool;
import by.epam.outercourse.project.devteam.entity.manager.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerDAO extends AbstractDAO<Integer, Manager> {
    /**
     * ManagerDAO class has got access to database's information about 'Manager'.
     * Class has got methods for CRUD-operations.
     * Class methods get SQL-queries from file 'sql_ru_RU.properties'.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(ManagerDAO.class);

    ResourceBundle resource = ResourceBundle.getBundle("sql");

    @Override
    public int findId(Manager entity) {
        int id = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("manager.findId"));
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
            entity.setId(id);
        } catch (SQLException e) {
            logger.error("Failed to find the manager by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return id;
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> managers = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(resource.getString("manager.findAll"));
            while (resultSet.next()) {
                Manager manager = new Manager();
                manager.setId(resultSet.getInt("id"));
                manager.setName(resultSet.getString("name"));
                manager.setEmail(resultSet.getString("email"));
                managers.add(manager);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of managers", e);
        } finally {
            close(statement);
            release(connection);
        }
        return managers;
    }

    @Override
    public Manager findEntityById(Integer id) {
        Manager manager = new Manager();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("manager.findEntityById"));
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            manager.setId(resultSet.getInt("id"));
            manager.setName(resultSet.getString("name"));
            manager.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            logger.error("Failed to find the manager by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return manager;
    }

    @Override
    public boolean delete(Integer id) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("manager.delete"));
            statement.setInt(1, id);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to delete the manager", e);
        } finally {
            close(statement);
            release(connection);
        }
        return flag;
    }

    @Override
    public boolean delete(Manager entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Manager create(Manager entity) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("manager.insert"));
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.executeUpdate();
            entity.setId(findId(entity));
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to create a new manager", e);
        } finally {
            close(statement);
            release(connection);
        }
        return entity;
    }

    @Override
    public Manager update(Manager entity) {
        Manager manager = new Manager();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("manager.update"));
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
            manager = entity;
        } catch (SQLException e) {
            logger.error("Failed to update the developer", e);
        } finally {
            close(statement);
            release(connection);
        }
        return manager;
    }
}
