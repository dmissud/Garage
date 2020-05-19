package org.dbs.garage.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DoMenuGarage extends GarageAbstractServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> menu = new HashMap<>();
        menu.put("A propos de MyGarage", "/doAbout");
        menu.put("Supervision des Garages", "/doSupervision");
        menu.put("Enregistrement d'un Garage", "/doCreateGarage");
        menu.put("Dé-enregistrement de Garage", "/doDeleteGarage");
        request.setAttribute("menu", menu);
        this.getServletContext().getRequestDispatcher("/menu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}