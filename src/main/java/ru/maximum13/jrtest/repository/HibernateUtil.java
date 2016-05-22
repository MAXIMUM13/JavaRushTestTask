package ru.maximum13.jrtest.repository;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final Session session;

    static {
        try {
            Configuration configuration = new Configuration().configure();

            sessionFactory = configuration.buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (HibernateException exc) {
            System.err.println("Problem in session factory: " + exc.getMessage());
            throw new ExceptionInInitializerError(exc);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return session;
    }

    private HibernateUtil() {
    }
}
