/*
 * @(#)ShowSpecsServlet.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.servlet.manager;

import by.epam.outercourse.project.devteam.dao.ClientDAO;
import by.epam.outercourse.project.devteam.dao.ManagerDAO;
import by.epam.outercourse.project.devteam.dao.SpecificationDAO;
import by.epam.outercourse.project.devteam.dao.TaskDAO;
import by.epam.outercourse.project.devteam.entity.client.Client;
import by.epam.outercourse.project.devteam.entity.manager.Manager;
import by.epam.outercourse.project.devteam.entity.project.Specification;
import by.epam.outercourse.project.devteam.entity.project.Task;
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
import java.util.List;

@WebServlet("/by.epam.outercourse.project.devteam.servlet.manager.ShowSpecsServlet")
public class ShowSpecsServlet extends HttpServlet {
    /**
     * ShowSpecsServlet class implements POST-method to create a new manager or find existing in DB
     * by name and email. The manager gets the list of specification with information about the client,
     * number of tasks in the specification and number of necessary developers for specification.
     * The manager can create a new project by selected specification.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(ShowSpecsServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String managerEmail, managerName;
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        managerEmail = jsonObject.getString("email");
        managerName = jsonObject.getString("name");
        logger.info("Data from form: " + managerEmail + " " + managerName);
        Manager manager;
        ManagerDAO managerDAO = new ManagerDAO();
        List<Manager> managers = managerDAO.findAll();
        boolean flag = true;
        for (Manager m : managers) {
            if (managerEmail.equals(m.getEmail()) && managerName.equals(m.getName())) {
                manager = m;
                flag = false;
                break;
            }
        }
        if (flag) {
            manager = new Manager(managerName, managerEmail);
            managerDAO.create(manager);
        }
        SpecificationDAO specificationDAO = new SpecificationDAO();
        List<Specification> freeSpecs = specificationDAO.findFreeSpecs();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONArray specsJSON = new JSONArray();
        if (freeSpecs.isEmpty()) {
            specsJSON.put("There are not free specifications!");
            response.getWriter().write(specsJSON.toString());
            return;
        }
        ClientDAO clientDAO = new ClientDAO();
        for (Specification spec : freeSpecs) {
            Client client = clientDAO.findEntityById(spec.getIdClient());
            JSONObject specJSON = new JSONObject();
            specJSON.put("id", spec.getId());
            specJSON.put("clientName", client.getName());
            specJSON.put("clientEmail", client.getEmail());
            TaskDAO taskDAO = new TaskDAO();
            List<Task> tasks = taskDAO.findTasksByIdSpec(spec.getId());
            specJSON.put("tasks", tasks.size());
            int devs = 0;
            for (Task task : tasks) {
                devs += task.getNumberOfTeamLeads();
                devs += task.getNumberOfSeniors();
                devs += task.getNumberOfMiddles();
                devs += task.getNumberOfJuniors();
            }
            specJSON.put("devs", devs);
            specsJSON.put(specJSON);
        }
        response.getWriter().write(specsJSON.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
