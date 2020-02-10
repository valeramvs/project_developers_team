/*
 * @(#)CheckBillServlet.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.servlet.client;

import by.epam.outercourse.project.devteam.dao.ClientDAO;
import by.epam.outercourse.project.devteam.dao.ManagerDAO;
import by.epam.outercourse.project.devteam.dao.SpecificationDAO;
import by.epam.outercourse.project.devteam.entity.client.Client;
import by.epam.outercourse.project.devteam.entity.manager.Manager;
import by.epam.outercourse.project.devteam.entity.project.Specification;
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

@WebServlet("/by.epam.outercourse.project.devteam.servlet.client.CheckBillServlet")
public class CheckBillServlet extends HttpServlet {
    /**
     * CheckBillServlet class implements POST-method for clients to check their specification and
     * specification's bill. The method gets client's name and email and makes the table with all
     * information about client's specifications.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(CheckBillServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientEmail, clientName;
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        clientEmail = jsonObject.getString("email");
        clientName = jsonObject.getString("name");
        logger.info("Data from form: " + clientEmail + " " + clientName);
        Client client = new Client();
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.findAll();
        JSONArray clientBill = new JSONArray();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        boolean flag = true;
        for (Client c : clients) {
            if (clientEmail.equals(c.getEmail()) && clientName.equals(c.getName())) {
                client = c;
                flag = false;
                break;
            }
        }
        if (flag) {
            clientBill.put("Client with these email and name does not exist in database!");
            response.getWriter().write(clientBill.toString());
            return;
        }
        JSONObject clientJSON = new JSONObject();
        int id = client.getId();
        String name = client.getName();
        String email = client.getEmail();
        clientJSON.put("id", id);
        clientJSON.put("name", name);
        clientJSON.put("email", email);
        clientBill.put(clientJSON);
        SpecificationDAO specificationDAO = new SpecificationDAO();
        List<Specification> specs = specificationDAO.findSpecsByIdClient(client);
        ManagerDAO managerDAO = new ManagerDAO();
        JSONArray specsJSON = new JSONArray();
        for (int i = 0; i < specs.size(); i++) {
            JSONObject specJSON = new JSONObject();
            specJSON.put("idSpec", specs.get(i).getId());
            if (specs.get(i).getIdManager() != 0) {
                Manager manager = managerDAO.findEntityById(specs.get(i).getIdManager());
                specJSON.put("bill", specs.get(i).getBill());
                specJSON.put("managerName", manager.getName());
                specJSON.put("managerEmail", manager.getEmail());
            } else {
                specJSON.put("bill", "–");
                specJSON.put("managerName", "Manager is not defined");
                specJSON.put("managerEmail", "Spec is in progress. Try later.");
            }
            specsJSON.put(specJSON);
        }
        clientBill.put(specsJSON);
        response.getWriter().write(clientBill.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
