package BankSystem;

import DataBaseConnection.ClientProcessor;
import java.math.BigDecimal;
import java.util.*;

class BankServer {

    private static final BankServer INSTANCE = new BankServer();
    private final HashMap<Integer, Client> allTheClients = new HashMap<>();
    private BigDecimal totalBankState = new BigDecimal(0);

    private BankServer() {
    }

    private synchronized Client getCertainClientFromDB(int accountNumber) {
        return allTheClients.get(accountNumber);
    }

    public synchronized void updateTotalMoney(BigDecimal additionalQuantity) {
        totalBankState = totalBankState.add(additionalQuantity);
    }

    public void investingByOwner (double investmentAmount) {
        totalBankState = totalBankState.add(BigDecimal.valueOf(investmentAmount));
    }

    public synchronized double getTotalBankState() {
        return totalBankState.doubleValue();
    }

    public synchronized static BankServer getInstance() {
        return INSTANCE;
    }

    public int getPinNumber(int accountNumber) {
        return getCertainClientFromDB(accountNumber).getPinNumber();
    }

    public void setPinNumber(int accountNumber, int newPIN) {
        getCertainClientFromDB(accountNumber).setPinNumber(newPIN);
    }

    public synchronized void addClientToTheDataBase(Client client) {
        ClientProcessor.addClientToDataBase(client);
        totalBankState = totalBankState.add(client.getAccountState());
    }

    public synchronized Optional<Client> findClient(int inputtedAccountNumber) {
        return ClientProcessor.findClientInDatabase(inputtedAccountNumber);
    }

    public synchronized boolean isPinCorrect(int accountNumber, int inputtedPin) {
        return getCertainClientFromDB(accountNumber).getPinNumber() == inputtedPin;
    }

    public synchronized BigDecimal getTotalMoneyOfClient(int accountNumber) {
        return getCertainClientFromDB(accountNumber).getAccountState();
    }

    public void setTotalAmountOfClient(int accountNumber, BigDecimal newAmount) {
        getCertainClientFromDB(accountNumber).setAccountState(newAmount);
    }

    public String getOwnerName(int accountNumber) {
        return getCertainClientFromDB(accountNumber).getOwnerName();
    }
}