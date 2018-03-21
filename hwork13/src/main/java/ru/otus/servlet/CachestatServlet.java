package ru.otus.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.DBServiceHibernateImpl;
import ru.otus.DS.AddressDataSet;
import ru.otus.DS.DataSet;
import ru.otus.DS.PhoneDataSet;
import ru.otus.DS.UserDataSet;
import ru.otus.cache.MyCache;
import ru.otus.cache.MyCacheMBean;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CachestatServlet extends HttpServlet {

    private static final String STAT_PAGE_TEMPLATE = "cachestat.html";
    private static final int PERIOD_MS = 1000;
    private static final long WORK_TIME_MS = TimeUnit.MINUTES.toMillis(10);
    private static final long STEP_TIME_MS = TimeUnit.SECONDS.toMillis(5);
    private DBServiceHibernateImpl dbService;

    private MyCacheMBean<Integer, DataSet> myCache;

    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        myCache = (MyCache) context.getBean("myCache");
        dbService = (DBServiceHibernateImpl) context.getBean("dbService");
        new Thread(() -> {
            try {
                testHibernate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

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

    private Map<String, Object> getStat() {
        final Map<String, Object> statParamVal = new HashMap<>();
        statParamVal.put("refreshPeriod", String.valueOf(PERIOD_MS));
        statParamVal.put("maxElements",myCache.getmaxElements());
        statParamVal.put("lifeTimeMs",myCache.getlifeTimeMs());
        statParamVal.put("idleTimeMs",myCache.getidleTimeMs());
        statParamVal.put("isEternal",String.valueOf(myCache.getisEternal()));
        statParamVal.put("HitCount",myCache.getHitCount());
        statParamVal.put("MissCount",myCache.getMissCount());
        return statParamVal;
    }

    private void testHibernate() throws InterruptedException {
        UserDataSet uDataSet;
        try {
            System.out.println("$$$$$$$$$$ Status: " + dbService.getLocalStatus());
            dbService.insert(new UserDataSet("Vasya", 35, new AddressDataSet("620137, Ekaterinburg, Vilonov str., 1-1"),new PhoneDataSet("8888888")));
            dbService.insert(new UserDataSet("Petya", 45, new AddressDataSet("620039, Ekaterinburg, Mashinostroiteley str., 8-8"), new PhoneDataSet("9999999")));
            uDataSet = dbService.select(2,UserDataSet.class);
            dbService.insert(new PhoneDataSet("7777777",uDataSet));
            dbService.update("number","7777777","number","5555555", PhoneDataSet.class);
            dbService.update("street","620039, Ekaterinburg, Mashinostroiteley str., 8-8","street","620000,  Ekaterinburg, Lenina str., 8-8", AddressDataSet.class);
            System.out.println(uDataSet);
            uDataSet = dbService.selectByName("name","Vasya",UserDataSet.class);
            System.out.println(uDataSet);
            List<UserDataSet> luDS = dbService.selectAll(UserDataSet.class);
            for (DataSet ds : luDS) {
                System.out.println(ds);
            }
            List<AddressDataSet> laDS = dbService.selectAll(AddressDataSet.class);
            for (DataSet ds : laDS) {
                System.out.println(ds);
            }
            List<PhoneDataSet> lpDS = dbService.selectAll(PhoneDataSet.class);
            for (DataSet ds : lpDS) {
                System.out.println(ds);
            }
            long startTime = System.currentTimeMillis();
            int i=0;
            while (System.currentTimeMillis() - startTime < WORK_TIME_MS) {
                i++;
                System.out.println(dbService.select(1, UserDataSet.class));
                i++;
                System.out.println(dbService.select(2, UserDataSet.class));
                Thread.sleep(STEP_TIME_MS);
            }
            System.out.println("Total count: "+i);
        } finally {
            dbService.shutdown();
        }
    }

}
