/*
 * @(#)Runner.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.runner;

import by.epam.outercourse.project.devteam.connect.ConnectionPool;
import by.epam.outercourse.project.devteam.dao.*;
import by.epam.outercourse.project.devteam.entity.client.Client;
import by.epam.outercourse.project.devteam.entity.developer.Developer;
import by.epam.outercourse.project.devteam.entity.manager.Manager;
import by.epam.outercourse.project.devteam.entity.project.Specification;
import by.epam.outercourse.project.devteam.entity.project.Task;
import by.epam.outercourse.project.devteam.service.BillService;
import by.epam.outercourse.project.devteam.service.ClientService;
import by.epam.outercourse.project.devteam.service.ManagerService;
import by.epam.outercourse.project.devteam.service.SpecificationService;

import java.sql.SQLException;
import java.util.List;

public class Runner {
    /**
     * Runner class has got the start point of program.
     * The program is created for clients, managers and team of developers. A client introduces a specification
     * with tasks. Tasks have information about number of developers for every task and their qualification.
     * A manager gets the specification with tasks and finds free appropriate developers for every task.
     * Each developer sets necessary work time for solving the task. The manager counts the bill according to
     * developer's qualification and work time. Final bill is introduced to the client by the manager.
     *
     * All information store in the database. Connection with DB occurs by JDBC.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    public static void main(String[] args) throws SQLException {

        /*Client client = new Client("ExampleClientName", "ExampleClientEmail");
        ClientDAO clientDAO = new ClientDAO();
        clientDAO.create(client);
        ClientService clientService = new ClientService();
        Specification specification = clientService.introduceSpecification(client);
        Task task = new Task(1,1,1,1, specification.getId());
        SpecificationService specificationService = new SpecificationService();
        specificationService.addTask(task);
        Manager manager = new Manager("ExampleMangerName", "ExampleManagerEmail");
        ManagerDAO managerDAO = new ManagerDAO();
        manager = managerDAO.create(manager);
        ManagerService managerService = new ManagerService();
        managerService.createProject(specification, manager);
        DeveloperDAO developerDAO = new DeveloperDAO();
        List<Developer> developers = developerDAO.findAllForSetTime();
        BillService billService = new BillService();
        for (Developer dev : developers) {
            dev.setWorkTime(10.0);
            developerDAO.setWorkTime(dev, 10.0);
            billService.countBill(dev);
        }*/

        ConnectionPool.getInstance().shutdown();



   }
}
