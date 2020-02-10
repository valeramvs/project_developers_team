/*
 * @(#)QualificationDAO.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.dao;

import by.epam.outercourse.project.devteam.connect.ConnectionPool;
import by.epam.outercourse.project.devteam.entity.developer.Qualification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class QualificationDAO extends AbstractDAO<Integer, Qualification> {
    /**
     * QualificationDAO class has got access to database's information about 'Qualification'.
     * Class has got methods for CRUD-operations.
     * Class methods get SQL-queries from file 'sql_ru_RU.properties'.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(QualificationDAO.class);

    ResourceBundle resource = ResourceBundle.getBundle("sql");

    @Override
    public int findId(Qualification entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Qualification> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Qualification findEntityById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Qualification entity) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("qualification.delete"));
            statement.setString(1, entity.getQualification());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to delete the qualification", e);
        } finally {
            close(statement);
            release(connection);
        }
        return flag;
    }

    @Override
    public Qualification create(Qualification entity) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("qualification.insert"));
            statement.setString(1, entity.getQualification());
            statement.setDouble(2, entity.getSkill());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to create a new qualification", e);
        } finally {
            close(statement);
            release(connection);
        }
        return entity;
    }

    @Override
    public Qualification update(Qualification entity) {
        Qualification qualification = new Qualification();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("qualification.update"));
            statement.setString(1, entity.getQualification());
            statement.setDouble(2, entity.getSkill());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
            qualification = entity;
        } catch (SQLException e) {
            logger.error("Failed to update the qualification", e);
        } finally {
            close(statement);
            release(connection);
        }
        return qualification;
    }

    public Qualification findSkillByQualification(String quality) {
        Qualification qualification = new Qualification();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("qualification.findSkillByQualification"));
            statement.setString(1, quality);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            qualification.setSkill(resultSet.getDouble("skill"));
        } catch (SQLException e) {
            logger.error("Failed to find the skill", e);
        } finally {
            close(statement);
            release(connection);
        }
        return qualification;
    }
}
