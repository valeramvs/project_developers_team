/*
 * @(#)Project.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity.project;

import by.epam.outercourse.project.devteam.dao.DeveloperDAO;
import by.epam.outercourse.project.devteam.dao.TaskDAO;
import by.epam.outercourse.project.devteam.entity.developer.Developer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Project {
    /**
     * Project class turns to database for finding the free appropriate developer and setting
     * specification's and task's id to the developer.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(Project.class);

    /**
     * Methos setSpecAndTask(develoepr, specification, task) sets specification's and tasks's
     * id to the developer and update information in database.
     */
    public void setSpecAndTask(Developer developer, Specification specification, Task task) {
        developer.setIdSpec(specification.getId());
        developer.setIdTask(task.getId());
        DeveloperDAO developerDAO = new DeveloperDAO();
        developerDAO.update(developer);
    }

    /**
     * Method finds and appoints free developers to project and turns data for getting bill
     */
    public void appointToProject(Specification specification) {
        logger.info("Project was created.");
        TaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.findTasksByIdSpec(specification.getId());
        for (Task task : tasks) {
            for (int i = 0; i < task.getNumberOfTeamLeads(); i++) {
                DeveloperDAO developerDAO = new DeveloperDAO();
                Developer developer = developerDAO.findFreeDeveloperByQualification("TEAM LEADER");
                setSpecAndTask(developer, specification, task);
                logger.info("Free team-leader was appointed to the project");
            }
            for (int i = 0; i < task.getNumberOfSeniors(); i++) {
                DeveloperDAO developerDAO = new DeveloperDAO();
                Developer developer = developerDAO.findFreeDeveloperByQualification("SENIOR");
                setSpecAndTask(developer, specification, task);
                logger.info("Free senior developer was appointed to the project");
            }
            for (int i = 0; i < task.getNumberOfMiddles(); i++) {
                DeveloperDAO developerDAO = new DeveloperDAO();
                Developer developer = developerDAO.findFreeDeveloperByQualification("MIDDLE");
                setSpecAndTask(developer, specification, task);
                logger.info("Free middle developer was appointed to the project");
            }
            for (int i = 0; i < task.getNumberOfJuniors(); i++) {
                DeveloperDAO developerDAO = new DeveloperDAO();
                Developer developer = developerDAO.findFreeDeveloperByQualification("JUNIOR");
                setSpecAndTask(developer, specification, task);
                logger.info("Free junior developer was appointed to the project");
            }
        }
    }
}
