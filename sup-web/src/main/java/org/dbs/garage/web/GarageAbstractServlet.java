package org.dbs.garage.web;

import org.dbs.garage.usage.service.ServiceFactory;

import javax.servlet.http.HttpServlet;

public class GarageAbstractServlet extends HttpServlet {
    protected static ServiceFactory servicefactory;

    protected static ServiceFactory getServicefactory() {
        return servicefactory;
    }

    public static void setServicefactory(ServiceFactory servicefactory) {
        GarageAbstractServlet.servicefactory = servicefactory;
    }

    public GarageAbstractServlet() {
        super();
    }
}
