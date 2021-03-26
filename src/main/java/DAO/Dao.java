package DAO;

import models.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Dao<T extends Entity> {
    protected final SessionFactory sessionFactory;

    public Dao() {
        sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    /**
     * Taking all entity in table
     *
     * @return Entity with id
     */
    public abstract List<T> getAll();

    /**
     * Inserting entity into database
     *
     * @param entity Entity to insert
     * @return Entity with id
     */
    public T save(T entity) {
        Session session = sessionFactory.openSession();
        Transaction trans = session.beginTransaction();
        try {
            session.save(entity);
            trans.commit();
        }
        catch (Exception ex) {
            trans.rollback();
            throw ex;
        }
        finally {
            session.close();
        }

        return entity;
    }

    /**
     * Update entity in database
     *
     * @param entity Entity to update
     * @return Updated entity
     */
    public T update(T entity) {
        Session session = sessionFactory.openSession();
        Transaction trans = session.beginTransaction();
        try {
            session.update(entity);
            trans.commit();
        }catch (Exception ex) {
            trans.rollback();
            throw ex;
        }
        finally {
            session.close();
        }

        return entity;
    }

    /**
     * Searching for entity with id
     *
     * @param id Entity id to find
     * @return Entity with giving id
     */
    protected T getById(int id, Class<T> type) {
        return sessionFactory.openSession().get(type, id);
    }

    /**
     * Takes all entities from table
     *
     * @param sql HQL for pointing table in database
     * @return List of all entities in table
     */
    public List<T> getAll(String sql) {
        return (ArrayList<T>) sessionFactory.openSession().createQuery(sql).list();
    }

    /**
     * Deleting entity form database in specific table
     *
     * @param entity Entity to delete
     * @return True if succeed, false - otherwise
     */
    public void delete(T entity) {
        Session session = sessionFactory.openSession();
        Transaction trans = session.beginTransaction();

        try {
            session.delete(entity);
            trans.commit();
        }
        catch (Exception ex) {
            trans.rollback();
            throw ex;
        }
        finally {
            session.close();
        }
    }
}
