package ru.otus.hwork10;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.otus.hwork10.DS.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class DAOHiber {
    private Session session;

    public DAOHiber (Session session) {
        this.session = session;
    }

    public <T extends DataSet> void save(T dataSet) {
        session.save(dataSet);
    }

    public <T extends DataSet> void update(String nameOfFieldSeek, String valOfFieldSeek, String nameOfFieldSet, String valOfFieldSet, Class<T> dataSetClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<T> criteria = builder.createCriteriaUpdate(dataSetClass);
        Root<T> from = criteria.from(dataSetClass);
        criteria.set(from.get(nameOfFieldSet), valOfFieldSet);
        criteria.where(builder.equal(from.get(nameOfFieldSeek), valOfFieldSeek));
        session.createQuery(criteria).executeUpdate();
    }

    public <T extends DataSet> void delete(String nameOfField, String valOfField, Class<T> dataSetClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<T> criteria = builder.createCriteriaDelete(dataSetClass);
        Root<T> from = criteria.from(dataSetClass);
        criteria.where(builder.equal(from.get(nameOfField), valOfField));
        session.createQuery(criteria).executeUpdate();
    }

    public <T extends DataSet> T read(long id, Class<T> dataSetClass) {
        return session.load(dataSetClass, id);
    }

    public <T extends DataSet> T readByName(String nameOfField, String valOfField, Class<T> dataSetClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(dataSetClass);
        Root<T> from = criteria.from(dataSetClass);
        criteria.where(builder.equal(from.get(nameOfField), valOfField));
        Query<T> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public <T extends DataSet> List<T> readAll(Class<T> dataSetClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(dataSetClass);
        criteria.from(dataSetClass);
        return session.createQuery(criteria).list();
    }
}
