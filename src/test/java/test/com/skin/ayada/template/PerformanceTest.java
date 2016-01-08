package test.com.skin.ayada.template;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.template.JspTemplateFactory;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.ClassPath;

/**
 * @author weixian
 * @version 1.0
 */
public class PerformanceTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File file = new File("webapp\\test3.jsp");
	        File parent = file.getParentFile();
	        TemplateContext templateContext = TemplateManager.getTemplateContext(parent.getCanonicalPath(), true);
	        JspTemplateFactory jspTemplateFactory = new JspTemplateFactory();
	        String classPath = ClassPath.getClassPath();
	        System.out.println("CLASS_PATH: " + classPath);
	        jspTemplateFactory.setWork(new File("work").getAbsolutePath());
	        jspTemplateFactory.setClassPath(classPath);
	        jspTemplateFactory.setIgnoreJspTag(false);

	        templateContext.setTemplateFactory(jspTemplateFactory);
	        templateContext.getTemplateFactory().setIgnoreJspTag(false);
	        templateContext.getSourceFactory().setSourcePattern("*");

	        StringWriter stringWriter = new StringWriter();
	        Map<String, Object> context = new HashMap<String, Object>();
	        PageContext pageContext = templateContext.getPageContext(context, stringWriter);
	        pageContext.setAttribute("userList", getUserList());
	        Template template = templateContext.getTemplate(file.getName(), "utf-8");

	        /**
	         * 
	         */
        	template.execute(pageContext);
        	System.out.println(stringWriter.toString());
        	stringWriter.getBuffer().setLength(0);

	        /**
	         * warmed
	         */
	        for(int i = 0; i < 100; i++) {
	        	template.execute(pageContext);
	        	stringWriter.getBuffer().setLength(0);
	        }

	        int count = 50000;
	        long t1 = System.currentTimeMillis();
	        for(int i = 0; i < count; i++) {
	        	template.execute(pageContext);
	        	stringWriter.getBuffer().setLength(0);
	        }
	        long t2 = System.currentTimeMillis();
	        System.out.println("count: " + count + ", run time: " + (t2 - t1));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getUserList() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    	for(int i = 0; i < 5; i++) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("code", "123");
    		map.put("name", "123");
    		map.put("date", "2016-01-01 08:00:00");
    		map.put("value", 110.0f);
    		list.add(map);
    	}
    	return list;
    }
}
