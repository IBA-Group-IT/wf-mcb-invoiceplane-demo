package com.ibagroup.wf.intelia.robots.invoiceplanedemo;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.freedomoss.workfusion.utils.gson.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibagroup.wf.intelia.core.CommonConstants;
import com.ibagroup.wf.intelia.core.annotations.OnError;
import com.ibagroup.wf.intelia.core.annotations.Wire;
import com.ibagroup.wf.intelia.core.robots.UiRobotCapabilities;
import com.ibagroup.wf.intelia.systems.google.search.GoogleSearchRobot;
import com.ibagroup.wf.intelia.systems.google.search.pages.ResultPage;
import com.ibagroup.wf.intelia.systems.google.search.to.ProductSummaryTO;
import com.ibagroup.wf.intelia.systems.invoiceplane.to.ProductTO;

public class GoogleSearchCollectProductSummaryRobot extends UiRobotCapabilities implements GoogleSearchRobot {

    private static final Logger logger = LoggerFactory.getLogger(GoogleSearchCollectProductSummaryRobot.class);

    @Wire(name = TrainingConstants.PRODUCT_JSON)
    private String productJson;

    private ResultPage resultPage = null;

    private String resultJson = null;

    private long startTime;
    private long endTime;

    public String perform() {
        ProductTO productTO = GsonUtils.GSON.<ProductTO>fromJson(productJson, ProductTO.class);

        this.startTime = new Date().getTime();
        initRobot(productTO.getProductName());

        List<ProductSummaryTO> results = getResultPage().getResults();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.resultJson = gson.toJson(results);
        logger.debug(resultJson);

        this.endTime = new Date().getTime();

        return CommonConstants.SUCCESS_CLMN_PROCEED;
    }

	@OnError
    public String handleError() {
        this.endTime = new Date().getTime();

        return CommonConstants.SUCCESS_CLMN_FAILED;
    }

    public String getResultJson() {
		return resultJson;
	}

    public long getRpaDuration() {
        return this.endTime - this.startTime;
    }

    @Override
    public ResultPage getResultPage() {
        return resultPage;
    }

    @Override
    public void setResultPage(ResultPage resultPage) {
        this.resultPage = resultPage;
    }

}
