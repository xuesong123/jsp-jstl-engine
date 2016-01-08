/*
 * $RCSfile: AyadaViewResolver.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-1-6 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.web.spring;

/**
 * <p>Title: AyadaViewResolver</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class AyadaViewResolver {
    String templateContext;

    public AyadaViewResolver() {
        // setViewClass(AyadaView.class);
    }

    /**
     * @param viewName
     * @return AbstractUrlBasedView
     * @throws Exception
     */
    /*
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AyadaView ayadaView = (AyadaView)(super.buildView(viewName));
        ayadaView.setTemplateContext(this.templateContext);
        return ayadaView;
    }
    */

    /**
     * @return AyadaView
     */
    protected Class<AyadaView> requiredViewClass() {
        return AyadaView.class;
    }

    /**
     * @return the templateContext
     */
    public String getTemplateContext() {
        return this.templateContext;
    }

    /**
     * @param templateContext the templateContext to set
     */
    public void setTemplateContext(String templateContext) {
        this.templateContext = templateContext;
    }
}
