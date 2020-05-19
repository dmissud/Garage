package org.dbs.garage.web;

import org.dbs.garage.usage.port.in.RegisterGarageCmd;
import org.dbs.garage.usage.port.out.GarageDesc;
import org.dbs.garage.usage.port.out.LocationDesc;
import org.dbs.garage.usage.port.out.ExUnknowGarage;
import org.dbs.garage.usage.port.out.ExUnknowLocation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreateGarage")
public class DoCreateGarage extends GarageAbstractServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String location = request.getParameter("location");
        String name = request.getParameter("name");
        if (location != null) {
            createAGarage(name, location);
        }
        List<GarageDesc> lstDescGarage = getServicefactory().getConsultGarageStock().retrieveSupervisionOfGarage();
        request.setAttribute("lstDescGarage", lstDescGarage);
        this.getServletContext().getRequestDispatcher("/listDescOfGarage.jsp").forward(request, response);
    }

    private void createAGarage(String name, String location) {
        boolean garageNotExist = false;
        try {
            getServicefactory().getConsultGarageStock().retrieveGarageStockByName(name);
        } catch (ExUnknowGarage exUnknowGarage) {
            garageNotExist = true;
            exUnknowGarage.printStackTrace();
        }
        if (garageNotExist) {
            RegisterGarageCmd cmdCreateGarage = new RegisterGarageCmd(name, location);
            try {
                getServicefactory().getEnrichGarageStock().registerGarage(cmdCreateGarage);
            } catch (ExUnknowLocation exUnknowLocation) {
                exUnknowLocation.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<LocationDesc> lstDescLocation =
                getServicefactory().getConsultLocationStock().retrieveSupervisionOfLocation();
        request.setAttribute("lstDescLocation", lstDescLocation);
        this.getServletContext().getRequestDispatcher("/createAGarage.jsp").forward(request, response);
    }
}
