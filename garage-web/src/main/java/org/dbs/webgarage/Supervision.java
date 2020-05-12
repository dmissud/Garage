package org.dbs.webgarage;

import org.dbs.garage.application.port.out.GarageDesc;
import org.dbs.garage.application.port.out.IConsultGarageStock;
import org.dbs.garage.application.service.ConsultGarageStockImpl;
import org.dbs.garagexml.RepositoryOfGarageXmlImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Supervision")
public class Supervision extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IConsultGarageStock consultGarageStock;
        consultGarageStock = new ConsultGarageStockImpl(RepositoryOfGarageXmlImpl.getInstance());

        List<GarageDesc> lstDescGarage = consultGarageStock.retrieveSupervisionOfGarage();
        request.setAttribute("lstDescGarage", lstDescGarage);
        this.getServletContext().getRequestDispatcher("/listDescOfGarage.jsp").forward(request, response);
    }
}
