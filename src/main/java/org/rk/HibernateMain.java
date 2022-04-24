package org.rk;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.rk.entity.CategoryEnum;
import org.rk.entity.Customer;
import org.rk.entity.Item;
import org.rk.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;

public class HibernateMain {

    private static EntityManager entityManager;

    public static void main(String[] args) {

        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .buildSessionFactory()) {

            entityManager = (EntityManager) sessionFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            createCustomer("Karel", LocalDate.now());
            createItem("Lednice", BigDecimal.valueOf(15000), CategoryEnum.FRIDGE);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void createItem(String lednice, BigDecimal price, CategoryEnum category) {

        Item item = new Item();
        item.setName(lednice);
        item.setPrice(price);
        item.setCategory(category);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(item);
        transaction.commit();
    }

    private static void createCustomer(String karel, LocalDate now) {
    }
}
