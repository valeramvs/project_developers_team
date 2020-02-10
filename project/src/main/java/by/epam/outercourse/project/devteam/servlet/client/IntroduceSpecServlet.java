/*
 * @(#)IntroduceSpecServlet.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.servlet.client;

import by.epam.outercourse.project.devteam.dao.ClientDAO;
import by.epam.outercourse.project.devteam.entity.client.Client;
import by.epam.outercourse.project.devteam.entity.project.Specification;
import by.epam.outercourse.project.devteam.entity.project.Task;
import by.epam.outercourse.project.devteam.service.ClientService;
import by.epam.outercourse.project.devteam.service.SpecificationService;
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

@WebServlet("/by.epam.outercourse.project.devteam.servlet.client.IntroduceSpecServlet")
public class IntroduceSpecServlet extends HttpServlet {
    /**
     * IntroduceSpecServlet class implements POST-method to create a new client or find existing in DB
     * by name and email. The client introduces a new specification  with the list of tasks and the list of
     * necessary developers for each task. All data send to DB.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(IntroduceSpecServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientEmail, clientName, clientSpec;
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        clientEmail = jsonObject.getString("email");
        clientName = jsonObject.getString("name");
        clientSpec = jsonObject.getString("spec");
        JSONArray tasksJSON = jsonObject.getJSONArray("tasks");
        int[][]tasks = new int[tasksJSON.length()][4];
        for (int i = 0; i < tasksJSON.length(); i++) {
            JSONArray taskJSON = tasksJSON.getJSONArray(i);
            for (int j = 0; j < taskJSON.length(); j++) {
                String value = (String)taskJSON.get(j);
                tasks[i][j] = Integer.parseInt(value);
            }
        }
        logger.info("Data from form: " + clientEmail + " " + clientName + " " + clientSpec);
        Client client = new Client();
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();
        boolean flag = true;
        for (Client c : clients) {
            if (clientEmail.equals(c.getEmail()) && clientName.equals(c.getName())) {
                client = c;
                flag = false;
                break;
            }
        }
        if (flag) {
            client = new Client(clientName, clientEmail);
            clientDAO.create(client);
        }
        ClientService clientService = new ClientService();
        Specification specification = clientService.introduceSpecification(client);
        SpecificationService specificationService = new SpecificationService();
        for (int i = 0; i < tasks.length; i++) {
            int numberOfTeamLeaders = tasks[i][0];
            int numberOfSeniors = tasks[i][1];
            int numberOfMiddles = tasks[i][2];
            int numberOfJuniors = tasks[i][3];
            Task task = new Task(numberOfTeamLeaders, numberOfSeniors, numberOfMiddles,
                            numberOfJuniors, specification.getId());
            specificationService.addTask(task);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write("Data sent to the manager!");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
