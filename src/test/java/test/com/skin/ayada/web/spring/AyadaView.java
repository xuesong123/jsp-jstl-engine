/*
 * $RCSfile: AyadaView.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-1-6 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.web.spring;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: AyadaView</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class AyadaView {
    String templateContext;

    public AyadaView() {
    }

    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
        /* throws NoSuchBeanDefinitionException, NoUniqueBeanDefinitionException */ {
        if(this.templateContext == null) {
            // this.templateContext = (String)(this.getApplicationContext().getBean("templateContext"));
        }

        String home = ""; // this.templateContext.getHome();
        String path = ""; // this.getUrl();
        path = path.replaceAll("//", "/");

        if(home.length() > 1) {
            if(path.startsWith(home)) {
                path = path.substring(home.length());
            }
        }

        // request.setAttribute("TemplateFilter$servletContext", this.servletContext);
        // TemplateDispatcher.dispatch(this.templateContext, request, response, path);
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
