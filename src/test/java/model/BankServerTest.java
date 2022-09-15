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
    }

    @Test
    void getInstance() {
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