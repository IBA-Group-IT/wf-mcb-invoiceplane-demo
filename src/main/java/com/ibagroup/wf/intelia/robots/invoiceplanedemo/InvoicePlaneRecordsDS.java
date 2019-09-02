package com.ibagroup.wf.intelia.robots.invoiceplanedemo;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import com.ibagroup.wf.intelia.core.FlowContext;
import com.ibagroup.wf.intelia.core.datastore.BaseDS;

public class InvoicePlaneRecordsDS extends BaseDS {
    public static final String PRODUCT_NAME_COLUMN = "product_name";
    public static final String GOOGLESEARCH_RPA_DURATION_COLUMN = "googlesearch_rpa_duration";
    public static final String INVOICEPLANE_RPA_DURATION_COLUMN = "invoiceplane_rpa_duration";
    public static final String PRODUCT_RESULT_JSON_COLUMN = "product_result_json";
    public static final String PROCESS_UUID_COLUMN = "PROCESS_UUID";

    @Inject
    public InvoicePlaneRecordsDS(FlowContext flowContext) {
        super(flowContext, TrainingConstants.INVOICEPLANE_RECORDS_DS);
    }

    public void addProductDetails(String processUuid, String productName, String googlesearchRpaDuration, String invoicePlaneRpaDuration, String productResultJson) {
        Map<String, String> recordMap = new HashMap<>();
        recordMap.put(PROCESS_UUID_COLUMN, processUuid);
        recordMap.put(GOOGLESEARCH_RPA_DURATION_COLUMN, googlesearchRpaDuration);
        recordMap.put(INVOICEPLANE_RPA_DURATION_COLUMN, invoicePlaneRpaDuration);
        recordMap.put(PRODUCT_RESULT_JSON_COLUMN, productResultJson);
        recordMap.put(PRODUCT_NAME_COLUMN, productName);

        insertRecord(recordMap);
    }

}
