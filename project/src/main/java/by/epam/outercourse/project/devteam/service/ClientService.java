/*
 * @(#)ClientService.java   1.0 2020/01/17
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.service;

import by.epam.outercourse.project.devteam.dao.SpecificationDAO;
import by.epam.outercourse.project.devteam.entity.client.Client;
import by.epam.outercourse.project.devteam.entity.project.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientService {
    /**
     * ClientService class introduces the specification with tasks to a manager.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(ClientService.class);

    /**
     * Method introduceSpecification() creates new client and client's specification
     * and turns all information of these objects to database.
     */
    public Specification introduceSpecification(Client client) {
        SpecificationDAO specificationDAO = new SpecificationDAO();
        Specification specification = new Specification(client.getId());
        specification = specificationDAO.create(specification);
        logger.info("Client introduced the specification to Manager");
        return specification;
    }
}
