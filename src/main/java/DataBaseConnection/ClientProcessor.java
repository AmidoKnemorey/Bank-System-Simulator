package DataBaseConnection;

import BankSystem.Client;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class ClientProcessor {

    private static final String FIND_CLIENT_QUERY = "SELECT * FROM client_base.clients WHERE account_number = :account_number";

    public static Optional<Client> findClientInDatabase(int account_number) {
        try (SessionFactory sessionFactory = DataBaseSessionFactory.getSession(); Session session = sessionFactory.openSession()) {
           return Optional.ofNullable(session.createQuery(FIND_CLIENT_QUERY, Client.class).setParameter("account_number", account_number).getSingleResult());
        }
    }

    public static void addClientToDataBase(Client client) {
        SessionFactory sessionFactory = DataBaseSessionFactory.getSession();
             Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(client);
            transaction.commit();
        }
    }

