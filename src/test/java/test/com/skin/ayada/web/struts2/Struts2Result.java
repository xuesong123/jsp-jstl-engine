/*
 * $RCSfile: Struts2Result.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-1-6 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.web.struts2;

/**
 * <p>Title: Struts2Result</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Struts2Result /* extends StrutsResultSupport */ {
    private String contentType;
    
    /**
    private ReflectionProvider reflectionProvider;
    protected void doExecute(String uri, ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest)(actionContext.get(ServletActionContext.HTTP_REQUEST));
        HttpServletResponse response = (HttpServletResponse)(actionContext.get(ServletActionContext.HTTP_RESPONSE));

        if (!uri.startsWith("/")) {
        }

        Object action = invocation.getAction();
        Map<String, Object> values = reflectionProvider.getBeanMap(action);
        response.setContentType(this.contentType);

        // request.setAttribute("TemplateFilter$servletContext", this.servletContext);
        // TemplateDispatcher.dispatch(this.templateContext, request, response, path);
    }
    

    @Inject
    public void setReflectionProvider(ReflectionProvider reflectionProvider) {
        this.reflectionProvider = reflectionProvider;
    }
    */

    /**
     * @return the contentType
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
