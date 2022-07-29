package BankSystem;

import DataBaseConnection.CashDispenserProcessor;

import model.BankServer;
import model.CashDispenser;

public class DispenserMain {

    public static void main(String[] args) {

        /*  simulation of investing by owner just for fun   */

        BankServer.getInstance().investingByOwner(10000.0);

        /*  Creating several clients. Note, that several clients have already created with different money state and added to DB table. */

//        BankServer.getInstance().addClientToTheDataBase(new Client(123,"John Smith",4353, new BigDecimal(129)));
//        BankServer.getInstance().addClientToTheDataBase(new Client(124, "Janusz Kaminski",4354, new BigDecimal(500)));
//        BankServer.getInstance().addClientToTheDataBase(new Client(125, "Wilhelm Schneider", 4355, new BigDecimal(800)));
//        BankServer.getInstance().addClientToTheDataBase(new Client(126, "Vladimir Gromov",4444, new BigDecimal(2150)));
//        BankServer.getInstance().addClientToTheDataBase(new Client(127, "Adam Kowalski", 4445, new BigDecimal(3440)));
//        BankServer.getInstance().addClientToTheDataBase(new Client(128, "Helena Johnson", 6565, new BigDecimal(6000))); // 13019

        /*  Creating several dispensers. Note, that several dispensers have already created with different money state and added to DB table.   */

//        CashDispenserProcessor. addDispenserToDataBaseTable(new CashDispenser(new BigDecimal("1000"), "Saint Istvan st.", 1));
//        CashDispenserProcessor.addDispenserToDataBaseTable(new CashDispenser(new BigDecimal("3000"), "Nostradamus st.", 2));
//        CashDispenserProcessor.addDispenserToDataBaseTable(new CashDispenser(new BigDecimal("2500"), "Gagarin Yuri st.", 3));

        /*  getting the dispenser from database table   */

        CashDispenser dispenser = CashDispenserProcessor.getDispenserFromDataBaseTable(2).orElseThrow();

        /*  putting the card and start program   */

        dispenser.putCard();

    }
}