package crypto;

import java.io.Serializable;
import java.util.List;

public class Block implements Serializable {

    int index;
    long timestamp;
    List<Transaction> transactions;
    int proof;
    String previousHash;

    Block(int index, long timestamp, List<Transaction> transactions, int proof, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    int getIndex() {
        return index;
    }

}
