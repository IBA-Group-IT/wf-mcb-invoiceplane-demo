package com.ibagroup.wf.intelia.robots.invoiceplanedemo;

import javax.inject.Inject;
import com.ibagroup.wf.intelia.core.annotations.Wire;
import com.ibagroup.wf.intelia.core.robots.RobotCapabilities;
import com.ibagroup.wf.intelia.systems.invoiceplane.to.ProductTO;

public class SaveRecordToDS extends RobotCapabilities {

    @Wire(name = TrainingConstants.PRODUCT_JSON)
    private ProductTO productTO;

    @Wire(name = TrainingConstants.GOOGLESEARCH_RPA_DURATION)
    private String googlesearchRpaDuration;

    @Wire(name = TrainingConstants.INVOICEPLANE_RPA_DURATION)
    private String invoiceplaneRpaDuration;

    @Wire(name = TrainingConstants.PRODUCT_RESULT_JSON)
    private String productResultJson;


    @Inject
    private InvoicePlaneRecordsDS invoicePlaneRecordsDS;

    public void perform() {
        String processGuid = getFlowContext().getProcessGuid();
        invoicePlaneRecordsDS.addProductDetails(processGuid, productTO.getProductName(), googlesearchRpaDuration, invoiceplaneRpaDuration, productResultJson);
	}
}