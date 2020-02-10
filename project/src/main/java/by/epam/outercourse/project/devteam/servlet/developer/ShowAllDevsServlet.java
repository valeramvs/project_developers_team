/*
 * @(#)ShowAllDevsServlet.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.servlet.developer;

import by.epam.outercourse.project.devteam.dao.DeveloperDAO;
import by.epam.outercourse.project.devteam.entity.developer.Developer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/by.epam.outercourse.project.devteam.servlet.developer.ShowAllDevsServlet")
public class ShowAllDevsServlet extends HttpServlet {
    /**
     * ShowAllDevsServlet class implements GET-method to make the table with all developers and their data from DB.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(ShowAllDevsServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DeveloperDAO developerDAO = new DeveloperDAO();
        List<Developer> developers = developerDAO.findAll();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONArray devsJSON = new JSONArray();
        for (Developer dev : developers) {
            JSONObject devJSON = new JSONObject();
            devJSON.put("id", dev.getId());
            devJSON.put("qualification", dev.getQualification());
            devJSON.put("workTime", dev.getWorkTime());
            devJSON.put("idSpec", dev.getIdSpec());
            devJSON.put("idTask", dev.getIdTask());
            devsJSON.put(devJSON);
        }
        response.getWriter().write(devsJSON.toString());
    }
}
