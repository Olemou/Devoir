/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipsl.ugb.edu.projet_crypthographie.EXO1;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Olemou felix sekou mohamed
 */
public class Question1_Question2 {

    public static PublicKey publickey;
    public static PrivateKey privatekey;
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public KeyPairGenerator GEN;
    public KeyPair pair;

    public Question1_Question2() throws NoSuchAlgorithmException {
        this.GEN = KeyPairGenerator.getInstance("RSA");
        //on doit une taille de 2048 bits
        GEN.initialize(2048);

        //Génération des clefs public et privee
        this.pair = GEN.generateKeyPair();

        this.privateKey = pair.getPrivate();

        this.publicKey = pair.getPublic();
    }
//on construit des getters capable de retourner la cle privee et public

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public KeyPairGenerator getGEN() {
        return GEN;
    }

    public KeyPair getPair() {
        return pair;
    }
//on execute le code de Generation et de chiffrement RSA-RAW

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
        // Afichage des cles public et privee
        Question1_Question2 p1 = new Question1_Question2();
        publickey = p1.getPublicKey();
        privatekey = p1.getPrivateKey();
        System.out.println("la cle privee est" + privatekey);
        System.out.println("la cle public est" + publickey);

        //Début du chiffrement (RSA_RAW)
        Scanner sc = new Scanner(System.in);
        //Saisir le message a chiffre
        System.out.println("Veuillez saisir le message à crypter");
        String secretMessage = sc.nextLine();

        System.out.println("Le message initial est:" + secretMessage);

        //Chiffement de la clé publique(RSA-RAW)
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publickey);

        //
        byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
        System.out.println("Message secret en byte:" + secretMessageBytes);
        System.out.println("Message crypté en byte:" + encryptedMessageBytes);

        //Encodage
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        System.out.println("Encodage dans la base 64 :" + encodedMessage);
        //Fin du chiffrement
        //partie RSA-RAW

        //Début de dechiffrement du message chiffre
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privatekey);

        byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        //Résultat
        System.out.println("Le message déchiffré est :" + decryptedMessage);
    }
}



