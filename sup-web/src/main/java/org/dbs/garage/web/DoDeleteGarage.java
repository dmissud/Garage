package org.dbs.garage.web;

import org.dbs.garage.usage.port.in.ExGarageGotVehicles;
import org.dbs.garage.usage.port.in.UnregisterGarageCmd;
import org.dbs.garage.usage.port.in.RegisterGarageCmd;
import org.dbs.garage.usage.port.out.GarageDesc;
import org.dbs.garage.usage.port.out.ExUnknowGarage;
import org.dbs.garage.usage.port.out.ExUnknowLocation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DoDeleteGarage")
public class DoDeleteGarage extends GarageAbstractServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] garagesNames = request.getParameterValues("garages");

        for(String garageName:garagesNames) {
            unregisterAGarage(garageName);
        }
        List<GarageDesc> lstDescGarage =
                getServicefactory().getConsultGarageStock().retrieveSupervisionOfGarage();
        request.setAttribute("lstDescGarage", lstDescGarage);
        this.getServletContext().getRequestDispatcher("/WEB-INF/listDescOfGarage.jsp").forward(request, response);
    }

    private void unregisterAGarage(String garageName) {
        UnregisterGarageCmd unregisterGarageCmd = new UnregisterGarageCmd(garageName);
        try {
            getServicefactory().getEnrichGarageStock().unRegisterGarage(unregisterGarageCmd);
        } catch (ExUnknowGarage | ExGarageGotVehicles exGarage) {
            exGarage.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GarageDesc> lstDescGarage =
                getServicefactory().getConsultGarageStock().retrieveGarageWithLowStock(0);
        request.setAttribute("lstDescGarage", lstDescGarage);
        this.getServletContext().getRequestDispatcher("/WEB-INF/deleteGarages.jsp").forward(request, response);
    }
}
