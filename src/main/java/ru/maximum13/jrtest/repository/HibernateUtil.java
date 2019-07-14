package ru.maximum13.jrtest.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final Session session;

    static {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            //Configuration configuration = new Configuration().configure();
            /*ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            session = sessionFactory.openSession();*/
            sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (Exception exc) {
            System.err.println("Problem in session factory: " + exc.getMessage());
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
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
