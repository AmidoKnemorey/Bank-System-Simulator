package model;

import BankSystem.IntegerInputHandler;
import BankSystem.NoSuchClientInDatabase;
import DataBaseConnection.CashDispenserProcessor;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public class CashDispenser implements DispenserInterface {

    private BigDecimal totalDispenserAmount;
    private String address;
    @Id
    private long id;

    public CashDispenser(BigDecimal totalAmount, String address, int id) {
        this.totalDispenserAmount = totalAmount;
        this.address = address;
        this.id = id;
        BankServer.getInstance().updateTotalMoney(totalAmount.negate());
    }

    private void changeDispenserMoneyState(BigDecimal amountToWithdrawOrDeposit) {
        this.totalDispenserAmount = this.totalDispenserAmount.add(amountToWithdrawOrDeposit);
        CashDispenserProcessor.addDispenserToDataBaseTable(this);
    }

    @Override
    public void putCard() {
        System.out.println("Welcome, please insert your card.");
        try {
            int accountNumber = IntegerInputHandler.getInstance().integerInput();
            System.out.println("Processing...");
            BankServer.getInstance().findClientInDatabase(accountNumber);
            System.out.printf("Good to see you the owner of card #%d\n", accountNumber);
            isPinCorrect(accountNumber);
        } catch (NoResultException | NoSuchClientInDatabase noResultException) {
            System.err.println("You are not a client of this bank.");
            System.exit(0);
        }
    }

    @Override
    public BigDecimal getTotalDispenserAmount() {
        return new BigDecimal(this.totalDispenserAmount.toString());
    }

    @Override
    public void isPinCorrect(int accountNumber) throws NoSuchClientInDatabase {
        System.out.print("\nPlease insert password of your card.\n");
        int inputtedPIN = IntegerInputHandler.getInstance().integerInput();
        if (BankServer.getInstance().isPinCorrect(accountNumber, inputtedPIN)) {
            System.out.printf("Password accepted dear %s.\n", BankServer.getInstance().getOwnerName(accountNumber));
            mainMenu(accountNumber);
        } else {
            System.err.println("Incorrect PIN.");
            System.exit(0);
        }
    }

    @Override
    public void setMoneyStateEverywhere(int accountNumber, BigDecimal amountToWithdrawOrDeposit) throws NoSuchClientInDatabase {
        if (amountToWithdrawOrDeposit.compareTo(BigDecimal.ZERO) != 0) {
            BankServer.getInstance().updateTotalMoney(amountToWithdrawOrDeposit);
            BankServer.getInstance().setTotalAmountOfClient(accountNumber, BankServer.getInstance().getTotalMoneyOfClient(accountNumber).add(amountToWithdrawOrDeposit));
            changeDispenserMoneyState(amountToWithdrawOrDeposit);
        } else {
            System.err.println("Shouldn't be a zero value of money amount you has entered.");
            System.exit(0);
        }
    }

    @Override
    public String getAddress() {
        return new String(this.address);
    }

    @Override
    public void mainMenu(int accountNumber) throws NoSuchClientInDatabase {
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
                    }
                    break;
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
                        System.out.println("Done. Your PIN number has changed.");
                    } else {
                        System.out.println("Incorrect password. You must reauthorize.");
                        System.exit(0);
                    }
                    break;
                case 4:
                    System.err.println("UNEXPECTED ERROR, SERVICE TEAM WILL BE HERE AS SOON AS POSSIBLE");
                    System.exit(0);
                case 5:
                    System.out.printf("ATM #%d on %s%n", id, getAddress());
                    break;
                case 6:
                    System.out.println("Good bye. Have a good time spending.\n-----------------------------------");
                    System.exit(0);
                case 7:
                    System.out.printf("You have %s dollars in your account.\n", BankServer.getInstance()
                            .getTotalMoneyOfClient(accountNumber));
                    break;
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
                Press [4] if you are under attack.
                Press [5] to get dispenser's address.
                Press [6] to quit.
                Press [7] to get money state""");
    }
}