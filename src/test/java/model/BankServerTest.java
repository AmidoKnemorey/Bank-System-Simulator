package model;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankServerTest {

    static BankServer bankServer;

    @BeforeAll
    static void setUp() {
       bankServer = BankServer.getInstance();
    }

    @Test
    void updateTotalMoney() {
        BigDecimal totalBankState = bankServer.getWholeBankSystemStateForTesting();
        assertEquals(totalBankState, bankServer.getWholeBankSystemStateForTesting());
        bankServer.updateTotalMoney(new BigDecimal(100));
        assertTrue(bankServer.getWholeBankSystemStateForTesting().compareTo(totalBankState) > 0);
    }

    @Test
    void investingByOwner() {
        BigDecimal totalBankState = bankServer.getWholeBankSystemStateForTesting();
        assertEquals(0, totalBankState.compareTo(bankServer.getWholeBankSystemStateForTesting()));
        bankServer.investingByOwner(100);
        assertEquals(-1, totalBankState.compareTo(bankServer.getWholeBankSystemStateForTesting()));
    }

    @Test
    void getInstance() {
        BankServer bankServerInstance = BankServer.getInstance();
        assertTrue(bankServerInstance.getClass().isInstance(bankServerInstance));
        assertSame(bankServerInstance.getClass(), BankServer.class);
    }

    @Test
    void setPinNumber() {
    }

    @Test
    void addNewClientToTheDataBase() {
    }

    @Test
    void updateExistingClientInDatabase() {
    }

    @Test
    void findClientInDatabase() {
    }

    @Test
    void isPinCorrect() {
    }

    @Test
    void getTotalMoneyOfClient() {
    }

    @Test
    void setTotalAmountOfClient() {
    }

    @Test
    void getOwnerName() {
    }
}