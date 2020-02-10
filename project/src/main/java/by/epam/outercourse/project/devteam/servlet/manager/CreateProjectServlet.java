/*
 * @(#)CreateProjectServlet.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.servlet.manager;

import by.epam.outercourse.project.devteam.dao.*;
import by.epam.outercourse.project.devteam.entity.manager.Manager;
import by.epam.outercourse.project.devteam.entity.project.Specification;
import by.epam.outercourse.project.devteam.service.ManagerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/by.epam.outercourse.project.devteam.servlet.manager.CreateProjectServlet")
public class CreateProjectServlet extends HttpServlet {
    /**
     * CreateProjectServlet class implements POST-method to create a new project by selected specification.
     * The method gets manager's name, email and specification's id.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(CreateProjectServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String managerEmail, managerName;
        int idSpec;
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        idSpec = jsonObject.getInt("idSpec");
        managerEmail = jsonObject.getString("email");
        managerName = jsonObject.getString("name");
        logger.info("Data from form: " + idSpec+ " " + managerEmail + " " + managerName);
        Manager manager = new Manager(managerName, managerEmail);
        ManagerDAO managerDAO = new ManagerDAO();
        int idManager = managerDAO.findId(manager);
        manager.setId(idManager);
        Specification specification;
        SpecificationDAO specificationDAO = new SpecificationDAO();
        specification = specificationDAO.findEntityById(idSpec);
        ManagerService managerService = new ManagerService();
        managerService.createProject(specification, manager);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONArray projectJSON = new JSONArray();
        projectJSON.put("The project by spec #" + idSpec + " was created!");
        response.getWriter().write(projectJSON.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
