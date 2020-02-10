/*
 * @(#)ManagerService.java   1.0 2020/01/17
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.service;

import by.epam.outercourse.project.devteam.dao.DeveloperDAO;
import by.epam.outercourse.project.devteam.dao.SpecificationDAO;
import by.epam.outercourse.project.devteam.entity.manager.Manager;
import by.epam.outercourse.project.devteam.entity.project.Project;
import by.epam.outercourse.project.devteam.entity.project.Specification;

public class ManagerService {
    /**
     * ManagerService class has methods for creating a project, appointing developers to tasks.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    /**
     * Method createProject(Specification specification, Manager manager) creates a new manager and a project
     * based on the specification. Method turns all information about these object to database.
     */
    public void createProject(Specification specification, Manager manager) {
        SpecificationDAO specificationDAO = new SpecificationDAO();
        specification.setIdManager(manager.getId());
        specificationDAO.update(specification);
        Project project = new Project();
        DeveloperDAO developerDAO = new DeveloperDAO();
        developerDAO.zeroWorkTime();
        project.appointToProject(specification);
    }
}
