package connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ConnectionFactory {

    private static SessionFactory SESSION_FACTORY;

    public static SessionFactory getSessionFactory() {
        if (SESSION_FACTORY == null) {
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(HibernateConfiguration.getHibernateConfiguration().getProperties())
                    .build();
            return HibernateConfiguration.getHibernateConfiguration().buildSessionFactory(standardServiceRegistry);
        } else {
            return SESSION_FACTORY;
        }
    }
}