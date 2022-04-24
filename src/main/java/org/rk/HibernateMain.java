package org.rk;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class HibernateMain {

    public static void main(String[] args) {
        final SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager entityManager = (EntityManager) sessionFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

    }
}
