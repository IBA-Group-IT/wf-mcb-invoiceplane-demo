package com.ibagroup.wf.intelia.robots.invoiceplanedemo;

import com.ibagroup.wf.intelia.core.datastore.DataStoreQuery;
import com.ibagroup.wf.intelia.core.robots.RobotCapabilities;
import com.ibagroup.wf.intelia.core.utils.BindingUtils;

public class WaitForAllRecords extends RobotCapabilities {

	public int perform() {
        String processGuid = BindingUtils.getWebHarvestTaskItem(getBinding()).getRun().getRootRunUuid();

        int rowsCount = new DataStoreQuery(getBinding()).executeQuery(TrainingConstants.INVOICEPLANE_RECORDS_DS,
                "select * from @this where PROCESS_UUID='" + processGuid + "'").getNumberOfRowsAffected();

        return rowsCount;
	}

}