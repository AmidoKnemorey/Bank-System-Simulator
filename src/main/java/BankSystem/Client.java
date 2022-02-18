package BankSystem;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;
import java.math.BigDecimal;

@Slf4j
@EqualsAndHashCode
@Entity
@Table(name = "clients")
@Setter
@SuppressWarnings("MySQLConfig")
public class Client {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number")
    private int account_number;
    @Column(name = "owner_name")
    private final String ownerName;
    @Column(name = "salt")
    private final String salt;
    @Column(name = "hashed_password")
    private final String hashed_password;
    @Column(name = "account_state")
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
        //this.pinNumber = pinNumber;
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