package crypto;

import java.io.Serializable;

public class Transaction implements Serializable {

    String sender;
    String recipient;
    int amount;

    public Transaction(String sender, String recipient, int amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

}
