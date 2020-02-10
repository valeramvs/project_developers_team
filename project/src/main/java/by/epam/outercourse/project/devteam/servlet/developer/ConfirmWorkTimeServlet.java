/*
 * @(#)ConfirmWorkTimeServlet.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.servlet.developer;

import by.epam.outercourse.project.devteam.dao.*;
import by.epam.outercourse.project.devteam.entity.developer.Developer;
import by.epam.outercourse.project.devteam.service.BillService;
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

@WebServlet("/by.epam.outercourse.project.devteam.servlet.developer.ConfirmWorkTimeServlet")
public class ConfirmWorkTimeServlet extends HttpServlet {
    /**
     * ConfirmWorkTimeServlet class implements POST-method to set and confirm developer's work time on the project.
     * After confirmation developers' work time, the client can get the total specification's bill.
     * The method gets the list of developers' id and the list of developers' work time.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(ConfirmWorkTimeServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        JSONArray idsJSON = jsonObject.getJSONArray("ids");
        JSONArray timesJSON = jsonObject.getJSONArray("times");
        int[] ids = new int[idsJSON.length()];
        double[] times = new double[timesJSON.length()];
        for (int i = 0; i < times.length; i++) {
            String id = (String)idsJSON.get(i);
            String time = (String)timesJSON.get(i);
            ids[i] = Integer.parseInt(id);
            times[i] = Double.parseDouble(time);
        }
        DeveloperDAO developerDAO = new DeveloperDAO();
        BillService billService = new BillService();
        for (int i = 0; i < ids.length; i++) {
            Developer developer = developerDAO.findEntityById(ids[i]);
            developer.setWorkTime(times[i]);
            developerDAO.setWorkTime(developer, times[i]);
            billService.countBill(developer);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write("Data was successfully added to the database!");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
