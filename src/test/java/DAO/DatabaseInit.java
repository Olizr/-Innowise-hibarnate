package DAO;

import models.Role;
import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;

import javax.persistence.Query;
import java.util.List;

public class DatabaseInit {
    public static void initDatabase(List<Role> roles, List<User> users) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for (Role i: roles) {
            session.save(i);
        }
        tx1.commit();

        tx1 = session.beginTransaction();
        for (User i: users) {
            session.save(i);
        }
        tx1.commit();
        session.close();


    }

    public static void clearDatabase() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("DELETE FROM Role").executeUpdate();
        session.createQuery("DELETE FROM User").executeUpdate();
        tx1.commit();
        session.close();
    }
}
