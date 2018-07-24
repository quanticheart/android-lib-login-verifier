package com.master.killercode.loginverifier;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by John on 21/02/2018.
 */

public class Encryption {


    private SecretKeySpec skeySpec;
    private Cipher cipher;

//       try {
//        String teste = "Hellow";
//// Sending side
//        byte[] data1 = teste.getBytes("UTF-8");
//        String base64 = Base64.encodeToString(data1, Base64.DEFAULT);
//        Log.w("data1 h", base64);
//// Receiving side
//        byte[] data2 = Base64.decode(base64, Base64.DEFAULT);
//        String text = new String(data2, "UTF-8");
//        Log.w("data1", text);
//// Sending side
//        byte[] data3 = teste.getBytes(StandardCharsets.UTF_8);
//        String base642 = Base64.encodeToString(data3, Base64.DEFAULT);
//        Log.w("data2 h", base642);
//// Receiving side
//        byte[] data4 = Base64.decode(base64, Base64.DEFAULT);
//        String text2 = new String(data4, StandardCharsets.UTF_8);
//        Log.w("data2", text2);
//
//    } catch (UnsupportedEncodingException e) {
//        e.printStackTrace();
//    }

    public Encryption(byte[] keyraw) throws Exception {
        if (keyraw == null) {
            byte[] bytesOfMessage = "".getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(bytesOfMessage);

            skeySpec = new SecretKeySpec(bytes, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } else {

            skeySpec = new SecretKeySpec(keyraw, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        }
    }

    public Encryption(String passphrase) throws Exception {
        byte[] bytesOfMessage = passphrase.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        skeySpec = new SecretKeySpec(thedigest, "AES");


        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    public Encryption() throws Exception {
        byte[] bytesOfMessage = "".getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        skeySpec = new SecretKeySpec(thedigest, "AES");

        skeySpec = new SecretKeySpec(new byte[16], "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    public byte[] encrypt(byte[] plaintext) throws Exception {
        //returns byte array encrypted with key

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        byte[] ciphertext = cipher.doFinal(plaintext);

        return ciphertext;
    }

    public byte[] decrypt(byte[] ciphertext) throws Exception {
        //returns byte array decrypted with key
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);

        byte[] plaintext = cipher.doFinal(ciphertext);

        return plaintext;
    }

}
