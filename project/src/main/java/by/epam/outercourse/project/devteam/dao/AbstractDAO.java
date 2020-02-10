/*
 * @(#)AbstractDAO.java   1.0 2019/11/25
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.dao;

import by.epam.outercourse.project.devteam.connect.ConnectionPool;
import by.epam.outercourse.project.devteam.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO<K, T extends Entity> {
    /**
     * AbstractDAO class is abstract class for all DAO-classes.
     * There are all determined methods for access to database and methods for closing
     * statement and connection to database.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(AbstractDAO.class);

    public abstract int findId(T entity);

    public abstract List<T> findAll();

    public abstract T findEntityById(K id);

    public abstract boolean delete(K id);

    public abstract boolean delete(T entity);

    public abstract T create(T entity);

    public abstract T update(T entity);

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
                logger.info("Statement is successfully closed.");
            }
        } catch (SQLException e) {
            logger.error("Failed to close statement", e);
        }
    }

    public void release(Connection connection) {
        try {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
                logger.info("Connection is successfully released.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Failed to close connection", e);
        }
    }
}
