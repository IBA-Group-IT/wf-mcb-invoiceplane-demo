package com.ibagroup.wf.intelia.robots.invoiceplanedemo;

import static com.ibagroup.wf.intelia.robots.invoiceplanedemo.TrainingConstants.GOOGLESEARCH_RPA_DURATION;
import static com.ibagroup.wf.intelia.robots.invoiceplanedemo.TrainingConstants.INVOICEPLANE_RPA_DURATION;
import static com.ibagroup.wf.intelia.robots.invoiceplanedemo.TrainingConstants.PRODUCT_NAME;
import static com.ibagroup.wf.intelia.robots.invoiceplanedemo.TrainingConstants.PRODUCT_RESULT_JSON;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import com.ibagroup.wf.intelia.core.annotations.Wire;
import com.ibagroup.wf.intelia.core.datastore.BaseDS;
import com.ibagroup.wf.intelia.core.mis.LoggableDetail;
import com.ibagroup.wf.intelia.core.mis.LoggableField;
import com.ibagroup.wf.intelia.core.mis.LoggableMethod;
import com.ibagroup.wf.intelia.core.robots.RobotCapabilities;
import com.ibagroup.wf.intelia.systems.invoiceplane.to.ProductTO;

public class AlternativeSaveRecordToDS extends RobotCapabilities {

    @LoggableDetail()
    @LoggableField()
    @Wire(name = TrainingConstants.PRODUCT_JSON)
    private ProductTO productTO;

    @Wire(name = TrainingConstants.GOOGLESEARCH_RPA_DURATION)
    private String googlesearchRpaDuration;

    @Wire(name = TrainingConstants.INVOICEPLANE_RPA_DURATION)
    private String invoiceplaneRpaDuration;

    @Wire(name = TrainingConstants.PRODUCT_RESULT_JSON)
    private String productResultJson;

    @Inject
    private BaseDS invoicePlaneRecordsDS;

    @LoggableMethod(module = "SaveRecordToDS", operation = "perform")
    public void perform() {
        Map<String, String> recordMap = new HashMap<>();
        recordMap.put("PROCESS_UUID", getFlowContext().getProcessGuid());
        recordMap.put(GOOGLESEARCH_RPA_DURATION, googlesearchRpaDuration);
        recordMap.put(INVOICEPLANE_RPA_DURATION, invoiceplaneRpaDuration);
        recordMap.put(PRODUCT_RESULT_JSON, productResultJson);
        recordMap.put(PRODUCT_NAME, productTO.getProductName());

        invoicePlaneRecordsDS.insertRecord(recordMap);
	}
}