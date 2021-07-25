/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipsl.ugb.edu.projet_crypthographie.EXO1;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

/**
 *
 * @author pc
 */
public class Question3 {

    /**
     *
     * @author olemou felix sekou
     */
    //on definit une fonction de chiffrement
    public static String encrypt(String publicKeyString, String message, boolean usingOAEP)
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(publicKeyString));
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(publicKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
        if (usingOAEP) {
            OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1",
                    MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey, oaepParameterSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        }
        byte[] encrypted = cipher.doFinal(message.getBytes());
        return new String(Base64.getEncoder().encode(encrypted));
    }
//on definit une fonction pour le dechiffrement

    public static String decrypt(String privateKeyString, String cipherText, boolean usingOAEP)
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(privateKeyString));
        PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
        if (usingOAEP) {
            OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1",
                    MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
            cipher.init(Cipher.DECRYPT_MODE, priKey, oaepParameterSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, priKey);
        }

        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText.getBytes()));
        return new String(decrypted);
    }

    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException {

        Question1_Question2 p1 = new Question1_Question2();
        Key publicKey = p1.getPublicKey();
        Key privateKey = p1.getPrivateKey();
        System.out.println("cle" + publicKey);
        String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        String privateKeyString = new String(Base64.getEncoder().encode(privateKey.getEncoded()));
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le message à crypter");
        String message = sc.nextLine();
        String cipherText = encrypt(publicKeyString, message, false);
        String plainText = decrypt(privateKeyString, cipherText, false);

        System.out.println("le message crypthe" +" "+ cipherText);
        System.out.println("le message decrypthe"+" " + plainText);
    }
}



    
    

 

