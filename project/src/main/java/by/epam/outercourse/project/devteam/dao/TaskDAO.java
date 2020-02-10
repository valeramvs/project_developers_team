/*
 * @(#)TaskDAO.java   1.0 2019/11/25
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.dao;

import by.epam.outercourse.project.devteam.connect.ConnectionPool;
import by.epam.outercourse.project.devteam.entity.project.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TaskDAO extends AbstractDAO<Integer, Task> {
    /**
     * TaskDAO class has got access to database's information about 'Task'.
     * Class has got methods for CRUD-operations.
     * Class methods get SQL-queries from file 'sql_ru_RU.properties'.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(TaskDAO.class);

    ResourceBundle resource = ResourceBundle.getBundle("sql");

    @Override
    public int findId(Task entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(resource.getString("task.findAll"));
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setNumberOfTeamLeads(resultSet.getInt("team_leaders"));
                task.setNumberOfSeniors(resultSet.getInt("seniors"));
                task.setNumberOfMiddles(resultSet.getInt("middles"));
                task.setNumberOfJuniors(resultSet.getInt("juniors"));
                task.setIdSpec(resultSet.getInt("spec_id"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of tasks", e);
        } finally {
            close(statement);
            release(connection);
        }
        return tasks;
    }

    @Override
    public Task findEntityById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Integer id) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("task.delete"));
            statement.setInt(1, id);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to delete the task", e);
        } finally {
            close(statement);
            release(connection);
        }
        return flag;
    }

    @Override
    public boolean delete(Task entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Task create(Task entity) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("task.insert"));
            statement.setInt(1, entity.getNumberOfTeamLeads());
            statement.setInt(2, entity.getNumberOfSeniors());
            statement.setInt(3, entity.getNumberOfMiddles());
            statement.setInt(4, entity.getNumberOfJuniors());
            statement.setInt(5, entity.getIdSpec());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to create a new task", e);
        } finally {
            close(statement);
            release(connection);
        }
        return entity;
    }

    @Override
    public Task update(Task entity) {
        Task task = new Task();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("task.update"));
            statement.setInt(1, entity.getNumberOfTeamLeads());
            statement.setInt(2, entity.getNumberOfSeniors());
            statement.setInt(3, entity.getNumberOfMiddles());
            statement.setInt(4, entity.getNumberOfJuniors());
            statement.setInt(5, entity.getIdSpec());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
            task = entity;
        } catch (SQLException e) {
            logger.error("Failed to update the task", e);
        } finally {
            close(statement);
            release(connection);
        }
        return task;
    }

    /**
     * Method findTasksByIdSPec(int idSpec) can find all tasks of the specification with right id.
     */
    public List<Task> findTasksByIdSpec(int idSpec) {
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("task.findByIdSpec"));
            statement.setInt(1, idSpec);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setNumberOfTeamLeads(resultSet.getInt("team_leaders"));
                task.setNumberOfSeniors(resultSet.getInt("seniors"));
                task.setNumberOfMiddles(resultSet.getInt("middles"));
                task.setNumberOfJuniors(resultSet.getInt("juniors"));
                task.setIdSpec(resultSet.getInt("spec_id"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            logger.error("Failed to find the task by specification's ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return tasks;
    }
}
