package DataBaseConnection;

import BankSystem.CashDispenser;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.Optional;

public class CashDispenserProcessor {

    private static final String FIND_DISPENSER_QUERY = "SELECT d FROM CashDispenser d WHERE id = :id";
    private static final String FIND_ALL_DISPENSERS_QUERY = "SELECT d FROM CashDispenser d";

    public static void addDispenserToDataBaseTable(CashDispenser dispenser) {
        try (Session session = DataBaseSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(dispenser);
            transaction.commit();
        }
    }

    public static Optional<CashDispenser> getDispenserFromDataBaseTable(long id) {
        try (Session session = DataBaseSessionFactory.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery(FIND_DISPENSER_QUERY, CashDispenser.class)
                    .setParameter("id", id)
                    .getSingleResult());
        }
    }

    public static BigDecimal getAllDispensersMoneyState() {
        try (Session session = DataBaseSessionFactory.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery(FIND_ALL_DISPENSERS_QUERY, CashDispenser.class)
                    .getResultList()
                    .stream()
                    .map(CashDispenser::getTotalDispenserAmount).
                    reduce(BigDecimal.ZERO, BigDecimal::add)).orElseThrow();
        }
    }
}
