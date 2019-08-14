package com.ibagroup.wf.intelia.robots.invoiceplanedemo;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import com.ibagroup.wf.intelia.core.CommonConstants;
import com.ibagroup.wf.intelia.core.annotations.OnError;
import com.ibagroup.wf.intelia.core.mis.LoggableDetail;
import com.ibagroup.wf.intelia.core.mis.LoggableField;
import com.ibagroup.wf.intelia.core.mis.LoggableMethod;
import com.ibagroup.wf.intelia.core.robots.UiRobotCapabilities;
import com.ibagroup.wf.intelia.systems.invoiceplane.InvoicePlaneSystem;
import com.ibagroup.wf.intelia.systems.invoiceplane.to.ProductTO;

public class InvoicePlaneCollectProjectsRobot extends UiRobotCapabilities {

    @LoggableField
    @LoggableDetail
    private List<ProductTO> products;

    private long startTime;
    private long endTime;

    @LoggableMethod(module = "mymodule", operation = "criticalActivity")
    public void someCriticalActivity() {
        System.out.println("Some Critical Activity");
    }


    @LoggableMethod(module = "mymodule", operation = "perform")
    public String perform() {
        this.startTime = new Date().getTime();
        someCriticalActivity();
        products = getInjector().getInstance(InvoicePlaneSystem.class).parseProducts();
        this.endTime = new Date().getTime();
        return CommonConstants.SUCCESS_CLMN_PROCEED;
    }

    @OnError
    public String handleError(Object self, Method thisMethod, Method proceed, Throwable throwable, Object[] args) {
        this.endTime = new Date().getTime();
        return CommonConstants.SUCCESS_CLMN_FAILED;
    }

    public List<ProductTO> getProducts() {
        return products;
    }

    public long getRpaDuration() {
        return this.endTime - this.startTime;
    }


}
