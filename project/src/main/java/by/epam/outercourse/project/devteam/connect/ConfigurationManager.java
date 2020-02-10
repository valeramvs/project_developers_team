/*
 * @(#)ConfigurationManager.java   1.0 2019/11/25
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.connect;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfigurationManager {
    /**
     * ConfigurationManager class reads configuration for connection to database
     * (url, user's name, password etc.) from file 'configuration_ru_RU.properties'.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static ConfigurationManager configurationManager = null;

    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("configuration_ru_RU");
    public static final String DRIVER = RESOURCE_BUNDLE.getString("db.driver");
    public static final String URL = RESOURCE_BUNDLE.getString("db.url");
    public static final String USER = RESOURCE_BUNDLE.getString("db.user");
    public static final String PASSWORD = RESOURCE_BUNDLE.getString("db.password");

    private ConfigurationManager(){}

    public static ConfigurationManager getConfigurationManager() throws SQLException {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }
}
