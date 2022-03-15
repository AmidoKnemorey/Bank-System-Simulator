package BankSystem;

import DataBaseConnection.ClientProcessor;

import java.math.BigDecimal;
import java.util.Optional;

public class DispenserMain {

    public static void main(String[] args) {


//        ClientProcessor.findClientInDatabase(123);

//        // to the DB
//        String salt = PasswordAdditive.generateSalt(10);
//        Optional<String> hashedPassword = PasswordAdditive.hashThePlainTextPassword("4355", salt);
//
//        //
//        PasswordAdditive.hashThePlainTextPassword("4353", salt);
//
//        System.out.println(PasswordAdditive.verifyThePassword("4355", hashedPassword.get(), salt));

//        BankServer.getInstance().investingByOwner(10000.0);
//
        // creating several clients
        BankServer.getInstance().addClientToTheDataBase(new Client(123,"John Smith",4353, new BigDecimal(129)));
        BankServer.getInstance().addClientToTheDataBase(new Client(124, "Janusz Kaminski",4354, new BigDecimal(500)));
        BankServer.getInstance().addClientToTheDataBase(new Client(125, "Wilhelm Schneider", 4355, new BigDecimal(800)));
        BankServer.getInstance().addClientToTheDataBase(new Client(126, "Vladimir Gromov",4444, new BigDecimal(2150)));
        BankServer.getInstance().addClientToTheDataBase(new Client(127, "Adam Kowalski", 4445, new BigDecimal(3440)));
        BankServer.getInstance().addClientToTheDataBase(new Client(128, "Helena Johnson", 6565, new BigDecimal(6000)));
//
//        // creating several dispensers
//        DispenserInterface<Client> dispenser = new CashDispenser(new BigDecimal("50"), "Saint Istvan st.", 1);
//        DispenserInterface<Client> dispenser2 = new CashDispenser(new BigDecimal("3000"), "Nostradamus st.", 2);
//        DispenserInterface<Client> dispenser3 = new CashDispenser(new BigDecimal("2500"), "Gagarin Yuri st.", 3);
//
//        // putting the card and start program
//        dispenser.putCard();

    }
}