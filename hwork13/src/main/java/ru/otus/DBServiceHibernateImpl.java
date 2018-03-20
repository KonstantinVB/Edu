package ru.otus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ru.otus.DS.*;
import ru.otus.cache.MyCache;
import ru.otus.cache.MyCacheMBean;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;
    private final MyCacheMBean<Integer, DataSet> myCache = new MyCache<Integer, DataSet> ();

        public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/otus_java_2017_10");
        configuration.setProperty("hibernate.connection.username", "KonstantinVB");
        configuration.setProperty("hibernate.connection.password", "14081972");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
        RegMyCachMBean(myCache);
    }

    public DBServiceHibernateImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private void RegMyCachMBean(MyCacheMBean<Integer, DataSet> myCache) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            mBeanServer.registerMBean(myCache, new ObjectName("ru.otus.hwork13.MyCache:type=MyCache"));
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (MBeanException e) {
            e.printStackTrace();
        }
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    public void drop() {
    }

    public void create() {
    }

    public <T extends DataSet> void insert(T dataSet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            DAOHiber dao = new DAOHiber(session);
            dao.save(dataSet);
            transaction.commit();
            Integer hashId=(String.valueOf(dataSet.getId())+dataSet.getClass().getName()).hashCode();
            myCache.put(hashId, dataSet);
        }
    }

    public <T extends DataSet> void update(String nameOfFieldSeek, String valOfFieldSeek, String nameOfFieldSet, String valOfFieldSet, Class<T> dataSetClass) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            DAOHiber dao = new DAOHiber(session);
            dao.update(nameOfFieldSeek,valOfFieldSeek,nameOfFieldSet, valOfFieldSet, dataSetClass);
            transaction.commit();
            List<T> DSlist;
            DSlist = dao.readAll(dataSetClass);
            DSlist.forEach((T) -> {
                myCache.put((String.valueOf(T.getId())+dataSetClass.getName()).hashCode(),T);
            });
        }
    }

    public <T extends DataSet> void delete(String nameOfField, String valOfField, Class<T> dataSetClass) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            DAOHiber dao = new DAOHiber(session);
            dao.delete(nameOfField,valOfField,dataSetClass);
            transaction.commit();
            List<T> DSlist;
            DSlist = dao.readAll(dataSetClass);
            DSlist.forEach((T) -> {
                myCache.put((String.valueOf(T.getId())+dataSetClass.getName()).hashCode(),T);
            });
        }
    }

    public <T extends DataSet> T select(long id, Class<T> dataSetClass) {
        return runInSession(session -> {
            Integer hashId=(String.valueOf(id)+dataSetClass.getName()).hashCode();
            T refDS = (T) myCache.get(hashId);
            if (refDS == null) {
                DAOHiber dao = new DAOHiber(session);
                refDS = dao.read(id,dataSetClass);
                myCache.put(hashId, refDS);
            }
            return refDS;
        });
    }

    public <T extends DataSet> T  selectByName(String nameOfField, String valOfField, Class<T> dataSetClass) {
        return runInSession(session -> {
            DAOHiber dao = new DAOHiber(session);
            T refDS = dao.readByName(nameOfField, valOfField,dataSetClass);
            Integer hashId=(String.valueOf(refDS.getId())+dataSetClass.getName()).hashCode();
            myCache.put(hashId, refDS);
            return refDS;
        });
    }

    public <T extends DataSet> List<T> selectAll(Class<T> dataSetClass) {
        return runInSession(session -> {
            DAOHiber dao = new DAOHiber(session);
            List<T> DSlist = dao.readAll(dataSetClass);
            DSlist.forEach((T) -> {
                myCache.put((String.valueOf(T.getId())+dataSetClass.getName()).hashCode(),T);
            });
            return DSlist;
        });
    }

    public void shutdown() {
        System.out.println("Hit count: "+myCache.getHitCount());
        System.out.println("Miss count: "+myCache.getMissCount());
        myCache.dispose();
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

}
