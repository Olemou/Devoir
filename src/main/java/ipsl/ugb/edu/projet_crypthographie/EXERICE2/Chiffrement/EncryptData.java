/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipsl.ugb.edu.projet_crypthographie.EXERICE2.Chiffrement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author pc
 */
public class EncryptData {
    
    
    

    
  


    private Cipher cipher;

    public EncryptData(File originalFile, File encrypted, SecretKeySpec secretKey, String cipherAlgorithm) 
        throws IOException, GeneralSecurityException{

        this.cipher = Cipher.getInstance(cipherAlgorithm);      
        encryptFile(getFileInBytes(originalFile), encrypted, secretKey);

    }

    public void encryptFile(byte[] input, File output, SecretKeySpec key) 
        throws IOException, GeneralSecurityException {

        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));

    }

    private void writeToFile(File output, byte[] toWrite) 
        throws IllegalBlockSizeException, BadPaddingException, IOException{

        output.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(toWrite);
        fos.flush();
        fos.close();
        System.out.println("Le fichier a été chiffrée avec succès et stockée dans : " + output.getPath());

    }

    public byte[] getFileInBytes(File f) throws IOException{

        FileInputStream fis = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;

    }
}

    



