package com.ibagroup.wf.invoiceplanedemo;

import java.util.Collection;
import java.util.Map;
import com.ibagroup.wf.intelia.core.Intelia;
import com.workfusion.intake.core.Module;
import groovy.lang.Binding;

public class InvoicePlaneDemo extends Intelia {

    public InvoicePlaneDemo(Binding context, Map<String, String> params, Collection<Module> additionalModules, Collection<Module> overrideModules, Object injectContext) {
        super(context, params, additionalModules, overrideModules, injectContext);
    }

    public static InvoicePlaneDemoBuilder init(Binding binding) {
        return new InvoicePlaneDemoBuilder(binding);
    }


    public static InvoicePlaneDemo defaultDemoSetup(Binding binding) {
        return init(binding).defaultDemoSetup().get();
    }
}
