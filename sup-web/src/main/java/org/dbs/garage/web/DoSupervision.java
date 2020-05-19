package org.dbs.garage.web;

import org.dbs.garage.usage.port.out.GarageDesc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Supervision")
public class DoSupervision extends GarageAbstractServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GarageDesc> lstDescGarage = getServicefactory().getConsultGarageStock().retrieveSupervisionOfGarage();
        request.setAttribute("lstDescGarage", lstDescGarage);
        this.getServletContext().getRequestDispatcher("/listDescOfGarage.jsp").forward(request, response);
    }
}
