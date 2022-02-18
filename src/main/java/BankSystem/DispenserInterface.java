package BankSystem;

import java.math.BigDecimal;

public interface DispenserInterface<Client> {

    void putCard();

    void isPinCorrect(int accountNumber) throws NoSuchClientInDatabase;

    String actionList ();

    String getAddress();

    void setMoneyStateEverywhere(int accountNumber, BigDecimal changedValue);

    void mainMenu(int accountNumber);

}