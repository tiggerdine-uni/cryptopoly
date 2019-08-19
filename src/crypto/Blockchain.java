package crypto;

import util.Convert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    List<Block> chain;
    List<Transaction> currentTransactions;

    Blockchain() {
        currentTransactions = new ArrayList<>();
        chain = new ArrayList<>();
        newBlock(100, "1");
    }

    /**
     * Create a new Block in the Blockchain
     *
     * @param proof The proof given by the Proof of Work algorithm
     * @param previousHash Hash of previous Block
     * @return New Block
     */
    Block newBlock(int proof, String previousHash) {
        if (previousHash == null) {
            previousHash = hash(lastBlock());
        }
        Block block = new Block(chain.size() + 1,System.currentTimeMillis() / 1000, new ArrayList<>(currentTransactions), proof, previousHash);
        // Reset the current list of transactions
        currentTransactions.clear();
        chain.add(block);
        return block;
    }

    /**
     * Creates a new transaction to go into the go mined Block
     *
     * @param sender Address of the Sender
     * @param recipient Address of the Recipient
     * @param amount Amount
     * @return The index of the Block that will hold this transaction
     */
    int newTransaction(String sender, String recipient, int amount) {
        currentTransactions.add(new Transaction(sender, recipient, amount));
        return lastBlock().getIndex() + 1;
    }
    /**
     * Creates a SHA-256 hash of a Block
     *
     * @param block Block
     * @return
     */
    static String hash(Block block) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(block);
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            return Convert.bytesToHex(sha256.digest(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Returns the last Block in the chain
    Block lastBlock() {
        return chain.get(chain.size() - 1);
    }

    /**
     * Simple Proof of Work Algorithm:
     *  - Find a number p' such that hash(pp') contains leading 4 zeroes, where p is the previous p'
     *  - p is the previous proof, and p' is the new proof
     *
     * @param lastBlock
     * @return
     */
    int proofOfWork(Block lastBlock) {
        int proof = 0;
        while (!validProof(lastBlock.proof, proof, hash(lastBlock))) {
            proof++;
        }
        return proof;
    }

    /**
     * Validates the Proof: Does hash(last_proof, proof) contain 4 leading zeroes?
     *
     * @param lastProof Previous Proof
     * @param proof Current Proof
     * @param lastHash
     * @return True if correct, False if not.
     */
    static boolean validProof(int lastProof, int proof, String lastHash) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
//            return Convert.bytesToHex(sha256.digest(Convert.intToBytes(lastProof * proof))).substring(0, 4).equals("0000");
            return Convert.bytesToHex(sha256.digest((lastProof + proof + lastHash).getBytes())).substring(0, 4).equals("0000");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
//        Block block = blockchain.newBlock(100, "1");
//        System.out.println(Blockchain.hash(block));

        System.out.println(blockchain.proofOfWork(blockchain.lastBlock()));
    }

}
