package model;


import BankSystem.PasswordAdditive;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
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

    public String getSalt() {
        return new String(this.salt);
    }

    protected void setPinNumber (int newPIN) {
        this.salt = PasswordAdditive.generateSalt(10);
        this.hashed_password = PasswordAdditive.hashThePlainTextPassword(String.valueOf(newPIN), this.salt).orElseThrow();
    }

    public String getHashedPassword() {
        return new String(this.hashed_password);
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