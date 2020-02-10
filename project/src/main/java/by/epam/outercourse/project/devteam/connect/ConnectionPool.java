/*
 * @(#)ConnectionPool.java   1.0 2019/11/25
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.connect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    /**
     * ConnectionPool class is self-developed connection pool to database by Singleton pattern.
     * Class gets the connection to database by JDBC.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;

    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 3;
    private static int MAX_POOL_SIZE = 5;

    private ConnectionPool() throws SQLException {
        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
            logger.info("New connection was added to connection pool.");
        }
    }
    public static ConnectionPool getInstance() throws SQLException {
        if(instance == null){
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * Method getConnection() get free connection from connection pool.
     * If connection pool is empty and number of connections is less than
     * MAX_POOL_SIZE, a new extra connection creates.
     */
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
                logger.info("New connection was added to connection pool.");
            } else {
                throw new RuntimeException("Maximum pool size have reached, no available connections!");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    /**
     * Method releaseConnection(Connection connection) put used connections
     * to connection pool again.
     */
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private  static Connection createConnection() throws SQLException {
        ConfigurationManager configurationManager = ConfigurationManager.getConfigurationManager();
        try {
            Class.forName(configurationManager.DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("Failed to found driver class", e);
        }
        return DriverManager.getConnection(configurationManager.URL, configurationManager.USER, configurationManager.PASSWORD);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    /**
     * Method shutdown() releases all used connection to connection pool,
     * closes all connections and clear connection pool.
     */
    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection connection : connectionPool) {
            connection.close();
            logger.info("Connection was closed.");
        }
        connectionPool.clear();
    }
}
