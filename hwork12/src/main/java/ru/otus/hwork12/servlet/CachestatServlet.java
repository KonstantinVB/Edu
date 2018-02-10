package ru.otus.hwork12.servlet;

import javax.management.*;
import java.lang.management.ManagementFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CachestatServlet extends HttpServlet {

    private static final String STAT_PAGE_TEMPLATE = "cachestat.html";

    private static final int PERIOD_MS = 1000;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Boolean checkAuth = (Boolean) request.getSession().getAttribute(LoginServlet.AUTH_PARAMETER);
        checkAuth = checkAuth != null ? checkAuth : false;
        if (checkAuth) {

            response.getWriter().println(TemplateProcessor.instance().getPage(STAT_PAGE_TEMPLATE, getStat()));

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private static Map<String, Object> getStat() {
        final Map<String, Object> statParamVal = new HashMap<>();
        final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        statParamVal.put("refreshPeriod", String.valueOf(PERIOD_MS));
        statParamVal.put("maxElements",0);
        statParamVal.put("lifeTimeMs",0);
        statParamVal.put("idleTimeMs",0);
        statParamVal.put("isEternal",0);
        statParamVal.put("HitCount",0);
        statParamVal.put("MissCount",0);
        try {
            ObjectName mbean = new ObjectName("ru.otus.hwork12.MyCache:type=MyCache");
            final MBeanAttributeInfo[] attributes = mBeanServer.getMBeanInfo(mbean).getAttributes();
            for (final MBeanAttributeInfo attribute : attributes) {
                try {
                    final Object value = mBeanServer.getAttribute(mbean,attribute.getName());
                    if (value == null) {
                        statParamVal.put(attribute.getName(),"");
                    } else {
                        statParamVal.put(attribute.getName(),value.toString());
                    }
                } catch (Exception e) {
                    statParamVal.put(attribute.getName(),"");
                }
            }
        } catch (  Exception e) {
            e.printStackTrace();
        }

        return statParamVal;
    }

}
