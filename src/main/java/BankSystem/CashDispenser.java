package BankSystem;

import java.math.BigDecimal;
import java.util.*;

public class CashDispenser implements DispenserInterface<Client> {

    private BigDecimal totalDispenserAmount;
    private final String address;
    private final int id;

    public CashDispenser(BigDecimal totalAmount, String address, int id) {
        this.totalDispenserAmount = totalAmount;
        this.address = address;
        this.id = id;
        BankServer.getInstance().updateTotalMoney(totalAmount.negate());
    }

    @Override
    public void putCard() {
        System.out.println("Welcome, please insert your card.");
        try {
            int accountNumber = IntegerInputHandler.getInstance().integerInput();
            BankServer.getInstance().findClient(accountNumber).orElseThrow(NoSuchClientInDatabase::new);
            System.out.printf("Good to see you the owner of card #%d\n", accountNumber);
            isPinCorrect(accountNumber);
        } catch (NoSuchElementException | NoSuchClientInDatabase noSuchElementException) {
            System.err.println("You are not a client of this bank.");
            System.exit(0);
        }
    }

    @Override
    public void isPinCorrect(int accountNumber) {
        System.out.print("\nPlease insert password of your card.\n");
        int inputtedPIN = IntegerInputHandler.getInstance().integerInput();
        if (BankServer.getInstance().isPinCorrect(accountNumber, inputtedPIN)){
            System.out.printf("Password accepted dear %s.\n", BankServer.getInstance().getOwnerName(accountNumber));
            mainMenu(accountNumber);
        } else {
            System.err.println("Incorrect PIN.");
            System.exit(0);
        }
    }

    @Override
    public void setMoneyStateEverywhere(int accountNumber, BigDecimal amountToWithdrawOrDeposit) {
        boolean firstActionDone = false;
        boolean secondActionDone = false;
        //TODO ATOMIC BLOCK HERE
        BankServer.getInstance().updateTotalMoney(amountToWithdrawOrDeposit);
        BankServer.getInstance().setTotalAmountOfClient(accountNumber, BankServer.getInstance().getTotalMoneyOfClient(accountNumber).add(amountToWithdrawOrDeposit));
        this.totalDispenserAmount = this.totalDispenserAmount.add(amountToWithdrawOrDeposit);
    }

    @Override
    public String getAddress() {
        return address;
    }


    @Override
    public void mainMenu(int accountNumber) {
        System.out.printf("You have %s dollars in your account.\n", BankServer.getInstance().getTotalMoneyOfClient(accountNumber));
        do {
            System.out.println(this.actionList());
            switch (IntegerInputHandler.getInstance().integerInput()) {
                case 1:
                    System.out.println("How many dollars to withdrawal?");
                    BigDecimal userEnteredAmount = BigDecimal.valueOf(IntegerInputHandler.getInstance().integerInput());
                    if (BankServer.getInstance().getTotalMoneyOfClient(accountNumber).compareTo(userEnteredAmount) < 0) {
                        System.out.println("It's not enough money on account.");
                    } else if (totalDispenserAmount.compareTo(userEnteredAmount) < 0) {
                        System.out.println("It's not enough money in dispenser.");
                    } else {
                        setMoneyStateEverywhere(accountNumber, userEnteredAmount.negate());
                        System.out.printf("""
                                Please take your money from the receiver below.
                                -----------------------------------------------
                                :            BANKNOTES TO TAKING              :
                                -----------------------------------------------
                                You have %s$ left in your account.
                                """, BankServer.getInstance().getTotalMoneyOfClient(accountNumber));
                    } break;
                case 2:
                    System.out.println("How many dollars to put?");
                    BigDecimal byUserInput = BigDecimal.valueOf(IntegerInputHandler.getInstance().integerInput());
                    setMoneyStateEverywhere(accountNumber, byUserInput);
                    System.out.printf("Processing.....\nYou have %s$ left in your account.", BankServer.getInstance().getTotalMoneyOfClient(accountNumber));
                    break;
                case 3:
                    System.out.println("Insert your current PIN number.");
                    if (BankServer.getInstance().isPinCorrect(accountNumber, IntegerInputHandler.getInstance().integerInput())) {
                        System.out.println("Insert new PIN number.");
                        BankServer.getInstance().setPinNumber(accountNumber, IntegerInputHandler.getInstance().integerInput());
                        System.out.println("Done. Your PIN number had changed.");
                    } else {
                        System.out.println("Incorrect password. You must reauthorize.");
                        System.exit(0);
                    } break;
                case 4:
                    System.out.println("Police will be here in 5 minutes and kick his ass.\nThe card will remain within dispenser.");
                    System.exit(0);
                case 5:
                    System.out.println("Good bye. Have a good time spending.\n-----------------------------------");
                    System.exit(0);
                case 6:
                    System.out.printf("ATM #%d on %s%n", id, getAddress());
                    break;
                case 7:
                    System.out.printf("Total bank state is: %s\n", BankServer.getInstance().getTotalBankState());
                default:
                    System.out.print("\nYou can enter only 1 - 7 digits.\n");
            }
        } while (true);
    }

    @Override
    public String actionList () {
        return ("""
                ------------------------------
                Choose one of the items below.
                ------------------------------
                Press [1] to cash withdrawal.
                Press [2] to put your money in your account.
                Press [3] to change your password.
                Press [4] if some asshole behind you with the gun.
                Press [5] to quit.
                Press [6] to get dispenser's address.
                Press [7] to check total bank state.""");
    }
}