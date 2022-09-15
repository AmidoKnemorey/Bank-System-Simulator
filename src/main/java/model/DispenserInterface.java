package model;

import BankSystem.NoSuchClientInDatabase;

import java.math.BigDecimal;

public interface DispenserInterface {

    void putCard();

    void isPinCorrect(int accountNumber) throws NoSuchClientInDatabase;

    String actionList ();

    String getAddress();

    BigDecimal getTotalDispenserAmount();

    void setMoneyStateEverywhere(int accountNumber, BigDecimal changedValue) throws NoSuchClientInDatabase;

    void mainMenu(int accountNumber) throws NoSuchClientInDatabase;

}