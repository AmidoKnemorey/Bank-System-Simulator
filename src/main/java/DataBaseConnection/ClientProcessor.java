package DataBaseConnection;

import BankSystem.Client;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ClientProcessor {

    private static final String FIND_CLIENT_QUERY = "SELECT c FROM Client c WHERE account_number = :account_number";
    private static final String GET_TOTAL_MONEY_QUERY = "SELECT c FROM Client c";

    public static Optional<Client> pullOutClientFromDatabase(int account_number) {
        try (Session session = DataBaseSessionFactory.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery(FIND_CLIENT_QUERY, Client.class)
                    .setParameter("account_number", account_number)
                    .getSingleResult());
        }
    }

    public static BigDecimal getTotalMoneyState () {
        BigDecimal totalAmount = new BigDecimal(0);
        try (Session session = DataBaseSessionFactory.getSessionFactory().openSession()) {
            List<Client> resultList = session.createQuery(GET_TOTAL_MONEY_QUERY, Client.class).getResultList();
            for (Client client : resultList) {
                totalAmount = totalAmount.add(client.getAccountState());
            }
        }
        return totalAmount;
    }

    public static void addOrUpdateClientToDataBase(Client client) {
        try (Session session = DataBaseSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(client);
            transaction.commit();
        }
    }
}