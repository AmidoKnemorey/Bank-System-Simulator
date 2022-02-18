package BankSystem;

import java.rmi.NoSuchObjectException;

public class NoSuchClientInDatabase extends NoSuchObjectException {
    public NoSuchClientInDatabase() {
        super("The client was not found in the bank database.");
    }
}