package ru.otus.hwork10;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import ru.otus.hwork10.DS.*;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;

/**

 */
public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

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

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    public void drop() {
        try (Session session = sessionFactory.openSession()) {
            SchemaExport export = new SchemaExport();
            MetadataSources metadataSources = new MetadataSources(new BootstrapServiceRegistryBuilder().build());
            StandardServiceRegistry sr = session.getSessionFactory().getSessionFactoryOptions().getServiceRegistry();
            Metadata metadata = metadataSources.buildMetadata(sr);
            export.drop(EnumSet.of(TargetType.DATABASE),metadata);
        }
    }

    public void create() {
        try (Session session = sessionFactory.openSession()) {
            SchemaExport export = new SchemaExport();
            MetadataSources metadataSources = new MetadataSources(new BootstrapServiceRegistryBuilder().build());
            StandardServiceRegistry sr = session.getSessionFactory().getSessionFactoryOptions().getServiceRegistry();
            Metadata metadata = metadataSources.buildMetadata(sr);
            export.create(EnumSet.of(TargetType.DATABASE), metadata);        }
    }

    public <T extends DataSet> void insert(T dataSet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            DAOHiber dao = new DAOHiber(session);
            dao.save(dataSet);
            transaction.commit();
        }
    }

    public <T extends DataSet> void update(String nameOfFieldSeek, String valOfFieldSeek, String nameOfFieldSet, String valOfFieldSet, Class<T> dataSetClass) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            DAOHiber dao = new DAOHiber(session);
            dao.update(nameOfFieldSeek,valOfFieldSeek,nameOfFieldSet, valOfFieldSet, dataSetClass);
            transaction.commit();
        }
    }

    public <T extends DataSet> void delete(String nameOfField, String valOfField, Class<T> dataSetClass) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            DAOHiber dao = new DAOHiber(session);
            dao.delete(nameOfField,valOfField,dataSetClass);
            transaction.commit();
        }
    }

    public <T extends DataSet> T select(long id, Class<T> dataSetClass) {
        return runInSession(session -> {
            DAOHiber dao = new DAOHiber(session);
            return dao.read(id,dataSetClass);
        });
    }

    public <T extends DataSet> T  selectByName(String nameOfField, String valOfField, Class<T> dataSetClass) {
        return runInSession(session -> {
            DAOHiber dao = new DAOHiber(session);
            return dao.readByName(nameOfField, valOfField,dataSetClass);
        });
    }

    public <T extends DataSet> List<T> selectAll(Class<T> dataSetClass) {
        return runInSession(session -> {
            DAOHiber dao = new DAOHiber(session);
            return dao.readAll(dataSetClass);
        });
    }

    public void shutdown() {
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
