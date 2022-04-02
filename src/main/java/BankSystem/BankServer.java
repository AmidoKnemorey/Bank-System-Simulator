package BankSystem;

import DataBaseConnection.CashDispenserProcessor;
import DataBaseConnection.ClientProcessor;
import java.math.BigDecimal;

class BankServer {

    private static final BankServer INSTANCE = new BankServer();
    private BigDecimal wholeBankSystemState; // investment + clients money + dispensers cash.

    private BankServer() {
        this.wholeBankSystemState = ClientProcessor.getTotalMoneyState();  // just fot update wholeBankSystemState field for testing the app
        this.wholeBankSystemState = this.wholeBankSystemState.subtract(CashDispenserProcessor.getAllDispensersMoneyState());
    }

    public synchronized void updateTotalMoney(BigDecimal additionalQuantity) {
        wholeBankSystemState = wholeBankSystemState.add(additionalQuantity);
    }

    public void investingByOwner (double investmentAmount) {
        wholeBankSystemState = wholeBankSystemState.add(BigDecimal.valueOf(investmentAmount));
    }

    public synchronized static BankServer getInstance() {
        return INSTANCE;
    }

    public synchronized void setPinNumber(int accountNumber, int newPin) throws NoSuchClientInDatabase {
        Client client = findClientInDatabase(accountNumber);
        client.setPinNumber(newPin);
        updateExistingClientInDatabase(client);
    }

    public synchronized void addNewClientToTheDataBase(Client client) {
        updateExistingClientInDatabase(client);
        wholeBankSystemState = wholeBankSystemState.add(client.getAccountState());
    }

    public synchronized void updateExistingClientInDatabase(Client client) {
        ClientProcessor.addOrUpdateClientToDataBase(client);
    }

    public synchronized Client findClientInDatabase(int inputtedAccountNumber) throws NoSuchClientInDatabase {
        return ClientProcessor.pullOutClientFromDatabase(inputtedAccountNumber).orElseThrow(NoSuchClientInDatabase::new);
    }

    public synchronized boolean isPinCorrect(int accountNumber, int inputtedPin) throws NoSuchClientInDatabase {
        Client currentClient = findClientInDatabase(accountNumber);
        return PasswordAdditive.verifyThePassword(String.valueOf(inputtedPin),
                currentClient.getHashedPassword(),
                currentClient.getSalt());
    }

    public synchronized BigDecimal getTotalMoneyOfClient(int accountNumber) throws NoSuchClientInDatabase {
        return findClientInDatabase(accountNumber).getAccountState();
    }

    public synchronized void setTotalAmountOfClient(int accountNumber, BigDecimal newAmount) throws NoSuchClientInDatabase {
        Client currentClient = findClientInDatabase(accountNumber);
        currentClient.setAccountState(newAmount);
        updateExistingClientInDatabase(currentClient);
    }

    public synchronized String getOwnerName(int accountNumber) throws NoSuchClientInDatabase {
        return findClientInDatabase(accountNumber).getOwnerName();
    }
}