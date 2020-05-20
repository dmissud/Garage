package org.dbs.garage.web;

import org.dbs.garage.infra.memory.RepositoryOfGarageMemoryImpl;
import org.dbs.garage.infra.memory.RepositoryOfLocationMemoryImpl;
import org.dbs.garage.infra.xml.RepositoryOfGarageXmlImpl;
import org.dbs.garage.infra.xml.RepositoryOfLocationXmlImpl;
import org.dbs.garage.usage.service.ConsultGarageStockImpl;
import org.dbs.garage.usage.service.ConsultLocationStockImpl;
import org.dbs.garage.usage.service.ManageGarageStockImpl;
import org.dbs.garage.usage.service.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DependencyInjectionListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent pServletContextEvent) {
        ServiceFactory serviceFactory = new ServiceFactory();
        serviceFactory.setConsultGarageStock(new ConsultGarageStockImpl(RepositoryOfGarageXmlImpl.getInstance()));
        serviceFactory.setEnrichGarageStock(new ManageGarageStockImpl(
                RepositoryOfGarageXmlImpl.getInstance(), RepositoryOfLocationXmlImpl.getInstance()));
        serviceFactory.setConsultLocationStock(new ConsultLocationStockImpl(RepositoryOfLocationXmlImpl.getInstance()));

        GarageAbstractServlet.setServicefactory(serviceFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent pServletContextEvent) {
        // NOP
    }
}

