package org.rk;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.rk.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
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

                Customer customerWithId1 = getObject(Customer.class, 1);
                List<Product> allObjects = getAllObjects(Product.class);

                log.info("Customer " + customerWithId1);
                log.info("Return to list" + allObjects);
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

        entityManager.persist(object);
        return object;

    }

    private static <T> T getObject(Class<T> clazz, int id) {
        T t = entityManager.find(clazz, id);
        return t;
    }

    private static <T> List<T> getAllObjects(Class<T> clazz) {
        Query query = entityManager.createQuery("FROM " + clazz.getName());
        return query.getResultList();
    }
}
