package crypto;

import util.Util;

import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Keys {

    /**
     * The length of each person's address.
     */
    private static final int ADDRESS_LENGTH = 32; // <= 256

    /**
     * The maps used to hold the public and private keys.
     */
    Map<String, PublicKey> publicKeys;
    Map<String, PrivateKey> privateKeys;

    /**
     * The key pair generator used to generate the keys pairs.
     */
    KeyPairGenerator keyGen;
//    SecureRandom random;

    Random r;

    Keys() {
        publicKeys = new HashMap<>();
        privateKeys = new HashMap<>();

        try {
            keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
//        random = SecureRandom.getInstance("SHA1PRNG", "SUN");
//        keyGen.initialize(1024, random);

        r = new Random();
    }

    /**
     * Generates keys and puts them in maps.
     *
     * @param name the person to generate keys for
     * @return true if successful
     */
    boolean add(String name) {
        if (publicKeys.containsKey(name)) {
            return false;
        }

        // Generate keys.
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        // Put keys in maps.
        publicKeys.put(name, pair.getPublic());
        privateKeys.put(name, priv);

        return true;
    }

    /**
     * Gets a public key.
     *
     * @param name the person whose public key to get
     * @return their public key
     */
    PublicKey getPublicKey(String name) {
        return publicKeys.get(name);
    }

    /**
     * Gets a private key.
     *
     * @param name the person whose private key to get
     * @return their private key
     */
    PrivateKey getPrivateKey(String name) {
        return privateKeys.get(name);
    }

    /**
     * Validates a private key.
     *
     * @param name the person whose private key to validate
     * @param privateKey the private key to validate
     * @return true if valid
     */
    boolean validate(String name, PrivateKey privateKey) {
        return privateKey.equals(privateKeys.get(name));
    }

    /**
     * Gets an address (the first ADDRESS_LENGTH characters of the person's public key).
     *
     * @param name the person whose address to get
     * @return their address
     */
    String getAddress(String name) {
        String s = getPublicKey(name).toString();
        s = s.substring(s.length() - 300).replaceAll("[^a-z0-9]","");
        return s.substring(0, ADDRESS_LENGTH);
    }

    /**
     * Gets an address (a random ADDRESS_LENGTH character substring of the person's public key).
     */
    String getRandomAddress(String name) {
        String s = getPublicKey(name).toString();
        s = s.substring(s.length() - 300).replaceAll("[^a-z0-9]","");
        return Util.randomSubstring(s, ADDRESS_LENGTH);
    }

    public static void main(String[] args) {
        Keys km = new Keys();
        km.add("Martin");
        km.add("Zuza");

//        PublicKey publicKey = km.getPublicKey("Martin");
//        System.out.println(publicKey.toString());

//        PrivateKey privateKey = km.getPrivateKey("Martin");
//        System.out.println(privateKey.toString());

        System.out.println(km.getAddress("Martin"));
        System.out.println(km.getAddress("Zuza"));
    }

}
