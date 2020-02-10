/*
 * @(#)ClientDAO.java   1.0 2019/11/25
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.dao;

import by.epam.outercourse.project.devteam.connect.ConnectionPool;
import by.epam.outercourse.project.devteam.entity.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientDAO extends AbstractDAO<Integer, Client> {
    /**
     * ClientDAO class has got access to database's information about 'Client'.
     * Class has got methods for CRUD-operations.
     * Class methods get SQL-queries from file 'sql_ru_RU.properties'.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(ClientDAO.class);

    ResourceBundle resource = ResourceBundle.getBundle("sql_ru_RU");

    @Override
    public int findId(Client entity) {
        int id = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("client.findId"));
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
            entity.setId(id);
        } catch (SQLException e) {
            logger.error("Failed to find the client by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return id;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(resource.getString("client.findAll"));
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                clients.add(client);
            }
        } catch (SQLException e) {
            logger.error("Failed to select the list of client", e);
        } finally {
            close(statement);
            release(connection);
        }
        return clients;
    }

    @Override
    public Client findEntityById(Integer id) {
        Client client = new Client();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("client.findEntityById"));
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("name"));
            client.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            logger.error("Failed to find the client by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return client;
    }

    @Override
    public boolean delete(Integer id) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("client.delete"));
            statement.setInt(1, id);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to delete the client by ID", e);
        } finally {
            close(statement);
            release(connection);
        }
        return flag;
    }

    @Override
    public boolean delete(Client entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Client create(Client entity) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("client.insert"));
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.executeUpdate();
            entity.setId(findId(entity));
            flag = true;
        } catch (SQLException e) {
            logger.error("Failed to create a new client", e);
        } finally {
            close(statement);
            release(connection);
        }
        return entity;
    }

    @Override
    public Client update(Client entity) {
        Client client = new Client();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(resource.getString("client.update"));
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
            client = entity;
        } catch (SQLException e) {
            logger.error("Failed to update the client", e);
        } finally {
            close(statement);
            release(connection);
        }
        return client;
    }

}
