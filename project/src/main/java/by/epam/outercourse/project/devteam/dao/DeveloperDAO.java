/*
 * @(#)DeveloperDAO.java   1.0 2019/11/25
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.dao;

import by.epam.outercourse.project.devteam.connect.ConnectionPool;
import by.epam.outercourse.project.devteam.entity.developer.Developer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeveloperDAO extends AbstractDAO<Integer, Developer> {
    /**
     * DeveloperDAO class has got access to database's information about 'Developer'.
     * Class has got methods for CRUD-operations.
     * Class methods get SQL-queries from file 'sql_ru_RU.properties'.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(DeveloperDAO.class);

    ResourceBundle resource = ResourceBundle.getBundle("sql");

    @Override
    public int findId(Developer entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> developers = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(resource.getString("developer.findAll"));
            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setId(resultSet.getInt("id"));
                developer.setQualification(resultSet.getString("qualification").toUpperCase());
                developer.setWorkTime(resultSet.getDouble("worktime"));
                developer.setIdSpec(resultSet.getInt("spec_id"));
                developer.setIdTask(resultSet.getInt("task_id"));
                developers.add(developer);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of developers", e);
        } finally {
            close(statement);
            release(connection);
        }
        return developers;
    }

    /**
     * Method findAllForSetTime() can find the list of developers which have been appointed to the project
     * and haven't set their work time on the project.
     */
    public List<Developer> findAllForSetTime() {
        List<Developer> developers = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(resource.getString("developer.findAllForSetTime"));
            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setId(resultSet.getInt("id"));
                developer.setQualification(resultSet.getString("qualification").toUpperCase());
                developer.setWorkTime(resultSet.getDouble("worktime"));
                developer.setIdSpec(resultSet.getInt("spec_id"));
                developer.setIdTask(resultSet.getInt("task_id"));
                developers.add(developer);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of developers to set work time", e);
        } finally {
            close(statement);
            release(connection);
        }
        return developers;
    }

    @Override
    public Developer findEntityById(Integer id) {
        Developer developer = new Developer();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("developer.findEntityById"));
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            developer.setId(resultSet.getInt("id"));
            developer.setQualification(resultSet.getString("qualification").toUpperCase());
            developer.setWorkTime(resultSet.getDouble("worktime"));
            developer.setIdSpec(resultSet.getInt("spec_id"));
            developer.setIdTask(resultSet.getInt("task_id"));
        } catch (SQLException e) {
            logger.error("Failed to find the developer by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return developer;
    }

    @Override
    public boolean delete(Integer id) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("developer.delete"));
            statement.setInt(1, id);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to delete the developer", e);
        } finally {
            close(statement);
            release(connection);
        }
        return flag;
    }

    @Override
    public boolean delete(Developer entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Developer create(Developer entity) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("developer.insert"));
            statement.setString(1, entity.getQualification().toUpperCase());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to create a new developer", e);
        } finally {
            close(statement);
            release(connection);
        }
        return entity;
    }

    @Override
    public Developer update(Developer entity) {
        Developer developer = new Developer();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("developer.update"));
            statement.setString(1, entity.getQualification().toUpperCase());
            statement.setDouble(2, entity.getWorkTime());
            statement.setInt(3, entity.getIdSpec());
            statement.setInt(4, entity.getIdTask());
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
            developer = entity;
        } catch (SQLException e) {
            logger.error("Failed to update the developer", e);
        } finally {
            close(statement);
            release(connection);
        }
        return developer;
    }

    /**
     * Method findFreeDeveloperByQualification(Qualification qualification) can find the first
     * developer who has appropriate qualification for task and isn't busy in the other task.
     */
    public Developer findFreeDeveloperByQualification(String qualification) {
        Developer developer = new Developer();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("developer.findByQualification"));
            statement.setString(1, qualification);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            developer.setId(resultSet.getInt("id"));
            developer.setQualification(resultSet.getString("qualification").toUpperCase());
        } catch (SQLException e) {
            logger.error("Failed to find the appropriate free developer", e);
        } finally {
            close(statement);
            release(connection);
        }
        return developer;
    }

    /**
     * Method setWorkTime(Developer developer, Double workTime) can set work time in project
     * for developer and update information about him in database.
     */
    public Developer setWorkTime(Developer developer, Double workTime) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("developer.setWorkTime"));
            statement.setDouble(1, workTime);
            statement.setInt(2, developer.getId());
            statement.executeUpdate();
            developer.setWorkTime(workTime);
        } catch (SQLException e) {
            logger.error("Failed to set work time for a developer", e);
        } finally {
            close(statement);
            release(connection);
        }
        return developer;
    }

    /**
     * Method zeroWorkTime() checks all developers without projects and zeroizes their work time in database.
     */
    public List<Developer> zeroWorkTime() {
        List<Developer> developers = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(resource.getString("developer.zeroWorkTime"));
            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setId(resultSet.getInt("id"));
                developer.setQualification(resultSet.getString("qualification").toUpperCase());
                developer.setWorkTime(resultSet.getDouble("worktime"));
                developer.setIdSpec(resultSet.getInt("spec_id"));
                developer.setIdTask(resultSet.getInt("task_id"));
                setWorkTime(developer, 0.0);
                developers.add(developer);
            }
        } catch (SQLException e) {
            logger.error("Failed to zero work time for free developers", e);
        } finally {
            close(statement);
            release(connection);
        }
        return developers;
    }

}
