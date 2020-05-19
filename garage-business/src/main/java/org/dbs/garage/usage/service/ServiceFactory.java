package org.dbs.garage.usage.service;

import org.dbs.garage.usage.port.in.IManageGarageStock;
import org.dbs.garage.usage.port.out.IConsultGarageStock;
import org.dbs.garage.usage.port.out.IConsultLocationStock;

public class ServiceFactory {
    private IConsultGarageStock consultGarageStock;
    private IConsultLocationStock consultLocationStock;
    private IManageGarageStock enrichGarageStock;

    public ServiceFactory() {
        super();
    }

    public IConsultGarageStock getConsultGarageStock() {
        return consultGarageStock;
    }

    public void setConsultGarageStock(IConsultGarageStock consultGarageStock) {
        this.consultGarageStock = consultGarageStock;
    }

    public IConsultLocationStock getConsultLocationStock() {
        return consultLocationStock;
    }

    public void setConsultLocationStock(IConsultLocationStock consultLocationStock) {
        this.consultLocationStock = consultLocationStock;
    }

    public IManageGarageStock getEnrichGarageStock() {
        return enrichGarageStock;
    }

    public void setEnrichGarageStock(IManageGarageStock enrichGarageStock) {
        this.enrichGarageStock = enrichGarageStock;
    }
}
