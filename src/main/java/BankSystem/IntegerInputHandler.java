package BankSystem;

import java.util.Scanner;

public class IntegerInputHandler {

    private static final Scanner scanner = new Scanner(System.in);
    private static final IntegerInputHandler INSTANCE = new IntegerInputHandler();

    public IntegerInputHandler() {}

    public static IntegerInputHandler getInstance() {
        return INSTANCE;
    }

    public int integerInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Incorrect inputted symbols. Must be only digits.");
            return this.integerInput();
        }
    }
}
