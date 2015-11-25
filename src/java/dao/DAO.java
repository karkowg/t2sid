package dao;

import java.util.List;
import model.Model;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class DAO<T extends Model> {

    private SessionFactory sessionFactory;
    private Transaction transaction = null;

    public DAO() {
        //session = HibernateUtil.getSessionFactory().getCurrentSession();
        HibernateUtil hu = new HibernateUtil();
        sessionFactory = hu.getSessionFactory();
    }
    
    public T insert(T t) {
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return t;
    }
    
    public T delete(T t) {
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return t;
    }
    
    public T update(T t) {
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return t;
    }
    
    public T search(String query) {
        List<T> l = list(query);
        return l.get(0);
    }
    
    public List<T> list(String query) {
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<T> l = session.createQuery(query).list();
        transaction.commit();
        session.close();
        return l;
    }
    
}
