package com.skin.ayada.template;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.JspFactory;
import com.skin.ayada.runtime.PageContext;

/**
 * @author weixian
 * @version 1.0
 */
public class Ayada {
	private static final TemplateFactory templateFactory = new TemplateFactory();
    private static final Logger logger = LoggerFactory.getLogger(Ayada.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            StringWriter out = new StringWriter();
            Ayada.execute("123<c:if test=\"${1 == 1}\">abc</c:if>xyz", null, out);
            System.out.println(out.toString());
        }
        catch(Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    /**
     * @param source
     * @param context
     * @return String
     * @throws Exception
     */
    public static String eval(String source, Map<String, Object> context) throws Exception {
    	StringWriter writer = new StringWriter();
        Template template = templateFactory.compile(source);
        PageContext pageContext = JspFactory.getPageContext(context, writer);
        template.execute(pageContext);
        return writer.toString();
    }

    /**
     * @param source
     * @param context
     * @param writer
     */
    public static void execute(String source, Map<String, Object> context, Writer writer) throws Exception {
        Template template = templateFactory.compile(source);
        PageContext pageContext = JspFactory.getPageContext(context, writer);
        template.execute(pageContext);
    }
}
