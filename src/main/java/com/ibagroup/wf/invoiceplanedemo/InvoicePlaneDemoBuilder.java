package com.ibagroup.wf.invoiceplanedemo;

import com.ibagroup.wf.intelia.core.InteliaBuilder;
import com.workfusion.intake.core.Module;
import groovy.lang.Binding;

/**
 * Sample of some APP custom builder
 *
 */
public class InvoicePlaneDemoBuilder extends InteliaBuilder<InvoicePlaneDemoBuilder> {

    public static class DemoModule implements Module {
    }

    public InvoicePlaneDemoBuilder(Binding context) {
        super(context);
    }

    public InvoicePlaneDemoBuilder defaultDemoSetup() {
        return defaultSetup().configFromDatastore("FrameworkTrainingConfig").defaultDatastore("invoiceplane_records").additional(DemoModule.class);
    }

    public InvoicePlaneDemo get() {
        return new InvoicePlaneDemo(getContext(), getParams(), getAdditionalModules(), getOverrideModules(), getInjectContext());
    }
}
