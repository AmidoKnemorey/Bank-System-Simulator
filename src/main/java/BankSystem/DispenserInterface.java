package BankSystem;

import java.math.BigDecimal;

public interface DispenserInterface {

    void putCard();

    void isPinCorrect(int accountNumber) throws NoSuchClientInDatabase;

    String actionList ();

    String getAddress();

    void setMoneyStateEverywhere(int accountNumber, BigDecimal changedValue) throws NoSuchClientInDatabase;

    void mainMenu(int accountNumber) throws NoSuchClientInDatabase;

}