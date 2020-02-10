/*
 * @(#)CheckSpecsServlet.java   1.0 2020/01/15
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

@WebServlet("/by.epam.outercourse.project.devteam.servlet.manager.CheckSpecsServlet")
public class CheckSpecsServlet extends HttpServlet {
    /**
     * CheckSpecsServlet class implements POST-method for managers to check their projects.
     * The method gets manager's name and email and makes the tables with personal information about the manager,
     * information about their project's client and project's tasks.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(CheckSpecsServlet.class);

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
        Manager manager = new Manager();
        ManagerDAO managerDAO = new ManagerDAO();
        List<Manager> managers = managerDAO.findAll();
        JSONArray managerJSON = new JSONArray();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        boolean flag = true;
        for (Manager m : managers) {
            if (managerEmail.equals(m.getEmail()) && managerName.equals(m.getName())) {
                manager = m;
                flag = false;
                break;
            }
        }
        if (flag) {
            managerJSON.put("Manager with these email and name does not exist in database!");
            response.getWriter().write(managerJSON.toString());
            return;
        }
        JSONObject personalJSON = new JSONObject();
        int id = manager.getId();
        String name = manager.getName();
        String email = manager.getEmail();
        personalJSON.put("id", id);
        personalJSON.put("name", name);
        personalJSON.put("email", email);
        managerJSON.put(personalJSON);
        SpecificationDAO specificationDAO = new SpecificationDAO();
        List<Specification> specs = specificationDAO.findSpecsByIdManager(manager);
        ClientDAO clientDAO = new ClientDAO();
        JSONArray specsJSON = new JSONArray();
        for (Specification spec : specs) {
            JSONObject specJSON = new JSONObject();
            specJSON.put("idSpec", spec.getId());
            Client client = clientDAO.findEntityById(spec.getIdClient());
            specJSON.put("bill", spec.getBill());
            specJSON.put("clientName", client.getName());
            specJSON.put("clientEmail", client.getEmail());
            specsJSON.put(specJSON);
        }
        managerJSON.put(specsJSON);
        JSONArray tasksJSON = new JSONArray();
        for (Specification spec : specs) {
            TaskDAO taskDAO = new TaskDAO();
            List<Task> tasks = taskDAO.findTasksByIdSpec(spec.getId());
            for (Task task : tasks) {
                JSONObject taskJSON = new JSONObject();
                taskJSON.put("idTask", task.getId());
                taskJSON.put("teamLeaders", task.getNumberOfTeamLeads());
                taskJSON.put("seniors", task.getNumberOfSeniors());
                taskJSON.put("middles", task.getNumberOfMiddles());
                taskJSON.put("juniors", task.getNumberOfJuniors());
                taskJSON.put("idSpec", task.getIdSpec());
                tasksJSON.put(taskJSON);
            }
        }
        managerJSON.put(tasksJSON);
        response.getWriter().write(managerJSON.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
