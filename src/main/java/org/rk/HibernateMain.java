package org.rk;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.rk.entity.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HibernateMain {

    private static Session entityManager;

    public static void main(String[] args) {

        try (SessionFactory sessionFactory = buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                entityManager = session;
                Transaction transaction = entityManager.getTransaction();
                transaction.begin();
                Customer customer = createCustomer("Karel", LocalDate.now());
                Product item = createItem("Lednice", BigDecimal.valueOf(15000), CategoryEnum.FRIDGE);
                Order order = createOrder(customer, item);
                transaction.commit();

                System.out.println(customer);
                System.out.println(item);
                System.out.println(order);
            }
        }
    }

    private static Order createOrder(Customer customer, Product... products) {
        Order order = new Order();
        order.setCustomer(customer);
        Set<OrderItem> itemList = Stream.of(products).map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(item);
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toSet());
        order.setItemList(itemList);
        return saveObject(order);
    }


    private static SessionFactory buildSessionFactory() {

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderItem.class)
                .addAnnotatedClass(Product.class)


                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        return sessionFactory;
    }

    private static Product createItem(String lednice, BigDecimal price, CategoryEnum category) {
        Product item = new Product();
        item.setName(lednice);
        item.setPrice(price);
        item.setCategory(category);
        return saveObject(item);
    }

    private static Customer createCustomer(String name, LocalDate birth) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setBirthDate(birth);
        return saveObject(customer);
    }

    private static <T> T saveObject(T object) {

        var id = (Serializable) entityManager.save(object);
        var savedObject = (T) entityManager.get(object.getClass(), id);
        return savedObject;

    }
}
