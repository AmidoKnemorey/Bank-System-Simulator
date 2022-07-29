package connection;

import model.CashDispenser;
import model.Client;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {

    public static Configuration getHibernateConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/client_base?serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", "sda103");
        configuration.setProperty("hibernate.connection.password", "sda103");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(CashDispenser.class);
        return configuration;
    }
}