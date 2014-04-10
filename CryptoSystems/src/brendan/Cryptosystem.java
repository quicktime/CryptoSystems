package brendan;

/**
 * Interface for various cryptosystems.
 * @author mrt
 */
public interface Cryptosystem {

    /**
     * Given a message string (the plaintext), return the ciphertext produced by
     * encrypting it with the current secret key. Any plaintext characters not
     * in the alphabet are put into the ciphertext unchanged.
     * @param plaintext the message to encrypt
     * @return the encrypted message (the ciphertext)
     */
    public String encrypt(String plaintext);
    
    /**
     * Given a ciphertext string, return the plaintext produced by decrypting it
     * with the current secret key. Any ciphertext characters not in the secret
     * key are put into the plaintext unchanged.
     * @param ciphertext
     * @return the decrypted plaintext.
     */
    public String decrypt(String ciphertext);
    
    /**
     * Return the secret key.
     * @return the secret key
     */
    public String getSecretKey();
    
}   //end interface
