package BankSystem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import java.math.BigDecimal;


@Entity
@Data
public class Client {

    @Id
    private int account_number;
    private String ownerName;
    private String salt;
    private String hashed_password;
    private BigDecimal accountState;


    public Client(int account_number, String ownerName, int pinNumber, BigDecimal accountState) {
        this.account_number = account_number;
        this.ownerName = ownerName;
        this.salt = PasswordAdditive.generateSalt(10);
        this.hashed_password = PasswordAdditive.hashThePlainTextPassword(String.valueOf(pinNumber), this.salt).orElseThrow();
        this.accountState = accountState;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getPinNumber() {
        return 1;
    }

    public void setPinNumber(int pinNumber) {
        // TODO ...
        this.hashed_password = PasswordAdditive.hashThePlainTextPassword(String.valueOf(pinNumber), this.salt).orElseThrow();;
    }

    public BigDecimal getAccountState() {
        return new BigDecimal(String.valueOf(accountState));
    }

    public void setAccountState(BigDecimal accountState) {
        this.accountState = accountState;
    }

    public String toString () {
        return String.format("%s", ownerName);
    }
}