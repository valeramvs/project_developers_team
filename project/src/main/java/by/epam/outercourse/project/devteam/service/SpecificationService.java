/*
 * @(#)SpecificationService.java   1.0 2020/01/17
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.service;

import by.epam.outercourse.project.devteam.dao.TaskDAO;
import by.epam.outercourse.project.devteam.entity.project.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpecificationService {
    /**
     * SpecificationService class has method 'addTask(Task task)' which creates the task
     * in database and adds the created task to the specification.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(SpecificationService.class);

    public void addTask(Task task) {
        TaskDAO taskDAO = new TaskDAO();
        taskDAO.create(task);
        logger.info("Task was added to specification");
    }
}

