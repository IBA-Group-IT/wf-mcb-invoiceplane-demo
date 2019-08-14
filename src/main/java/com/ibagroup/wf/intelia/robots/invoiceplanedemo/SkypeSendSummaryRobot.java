package com.ibagroup.wf.intelia.robots.invoiceplanedemo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.freedomoss.workfusion.utils.gson.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ibagroup.wf.intelia.core.CommonConstants;
import com.ibagroup.wf.intelia.core.annotations.OnError;
import com.ibagroup.wf.intelia.core.datastore.DataStoreQuery;
import com.ibagroup.wf.intelia.core.mis.LoggableMethod;
import com.ibagroup.wf.intelia.core.robots.UiRobotCapabilities;
import com.ibagroup.wf.intelia.core.security.SecureEntryDtoWrapper;
import com.ibagroup.wf.intelia.core.security.SecurityUtils;
import com.ibagroup.wf.intelia.core.utils.BindingUtils;
import com.ibagroup.wf.intelia.systems.google.search.to.ProductSummaryTO;
import com.ibagroup.wf.intelia.systems.skype.SkypeRobot;
import com.ibagroup.wf.intelia.systems.skype.pages.MainPage;
import com.workfusion.rpa.helpers.RPA;

public class SkypeSendSummaryRobot extends UiRobotCapabilities implements SkypeRobot {

    private static final Logger logger = LoggerFactory.getLogger(SkypeSendSummaryRobot.class);

    private static final Type TYPE_PRODUCT_SUMMARY_ROW = new TypeToken<List<ProductSummaryTO>>() {}.getType();

    private MainPage mainPage = null;

    @LoggableMethod(module = "skype_send_summary", operation = "perform")
    public String perform() {
        RPA.enableTypeOnScreen();

        SecureEntryDtoWrapper credentials = new SecurityUtils(getBinding()).getSecureEntry("skype");
        initRobot(credentials);

        getMainPage().sendMessageToRecipient(getCfg().getConfigItem("skype.recipient_address"), getSummary());

        finiliseRobot();

        return CommonConstants.SUCCESS_CLMN_PROCEED;
    }

    private List<String> getSummary() {
        String processGuid = BindingUtils.getWebHarvestTaskItem(getBinding()).getRun().getRootRunUuid();

        String successfullyPassedRecordsSQL =
                "select * from @this where PROCESS_UUID = '" + processGuid + "'";

        Optional<List<Map<String, String>>> records =
                new DataStoreQuery(getBinding()).executeQuery(TrainingConstants.INVOICEPLANE_RECORDS_DS, successfullyPassedRecordsSQL).getSelectResultAsMapRows();

        List<String> result = new ArrayList<String>();
        if (records.isPresent()) {
            for (Map<String, String> row : records.get()) {
                String productResultJson = row.get(TrainingConstants.PRODUCT_RESULT_JSON);
                List<ProductSummaryTO> productSummaryList =
                        GsonUtils.GSON.<List<ProductSummaryTO>>fromJson(productResultJson, TYPE_PRODUCT_SUMMARY_ROW);

                StringBuffer sb = new StringBuffer();
                sb.append("===========================================").append("\n");
                sb.append("Product Name: " + row.get(TrainingConstants.PRODUCT_NAME)).append("\n\n");
                sb.append("Google Search RPA duration: " + row.get(TrainingConstants.GOOGLESEARCH_RPA_DURATION)).append("ms").append("\n");
                sb.append("Invoice Plane RPA duration: " + row.get(TrainingConstants.INVOICEPLANE_RPA_DURATION)).append("ms").append("\n");
                sb.append("Product links: ").append("\n");
                for (ProductSummaryTO summaryTO : productSummaryList) {
                    sb.append("\t").append(summaryTO.getProductLink()).append("\n");
                }
                sb.append("===========================================");

                result.add(sb.toString());
            }
        }

        return result;
    }

    @OnError
    public String handleError() {
        finiliseRobot();
        return CommonConstants.SUCCESS_CLMN_FAILED;
    }

    @Override
    public MainPage getMainPage() {
        return mainPage;
    }

    @Override
    public void setMainPage(MainPage mainPage) {
        this.mainPage = mainPage;
    }
}
