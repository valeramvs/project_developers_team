/*
 * @(#)ShowDevsWithoutTimeServlet.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.servlet.developer;

import by.epam.outercourse.project.devteam.dao.*;
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

@WebServlet("/by.epam.outercourse.project.devteam.servlet.developer.ShowDevsWithoutTimeServlet")
public class ShowDevsWithoutTimeServlet extends HttpServlet {
    /**
     * ShowDevsWithoutServlet class implements GET-method to show developers which were appointed to the project
     * and need to set their work time on this project.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(ShowDevsWithoutTimeServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DeveloperDAO developerDAO = new DeveloperDAO();
        List<Developer> developers = developerDAO.findAllForSetTime();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONArray devsJSON = new JSONArray();
        if (developers.isEmpty()) {
            devsJSON.put("There are not developers to set work time!");
            response.getWriter().write(devsJSON.toString());
            return;
        }
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
