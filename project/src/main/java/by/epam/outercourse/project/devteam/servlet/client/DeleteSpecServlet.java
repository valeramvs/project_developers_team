/*
 * @(#)DeleteSpecServlet.java   1.0 2020/01/15
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.servlet.client;

import by.epam.outercourse.project.devteam.dao.SpecificationDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/by.epam.outercourse.project.devteam.servlet.client.DeleteSpecServlet")
public class DeleteSpecServlet extends HttpServlet {
    /**
     * DeleteSpecServlet class implements POST-method to delete specification which haven't had the manager yet.
     * The method gets specification's id.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(DeleteSpecServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id;
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        id = jsonObject.getInt("id");
        SpecificationDAO specificationDAO = new SpecificationDAO();
        specificationDAO.delete(id);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
