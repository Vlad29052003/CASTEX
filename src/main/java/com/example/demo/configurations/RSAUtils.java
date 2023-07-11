package com.example.demo.configurations;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class RSAUtils implements InitializingBean {
    private static int index = 0;
    private static int counter = 0;
    private static final List<KeyPair> KEYS = new ArrayList<>(100);

    @Override
    public void afterPropertiesSet() {
        changeIndex();
        new Thread(() -> {
            try {
                generateKeyPairs();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void generateKeyPairs() throws NoSuchAlgorithmException {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                KeyPairGenerator kpg = null;
                try {
                    kpg = KeyPairGenerator.getInstance("RSA");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                kpg.initialize(2048);
                KEYS.add(kpg.generateKeyPair());
                counter++;
                if (counter == 99) System.out.println("Key generation complete!");
            }).start();
        }
    }

    public static String getPublicKey() {
        String key = "";
        try {
            key = convertToPEM(KEYS.get(index).getPublic());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static String encrypt(String data, String publicKeyPEM) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""));
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    public static String decrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, KEYS.get(index).getPrivate());
        changeIndex();
        return new String(cipher.doFinal(data));
    }

    private static String convertToPEM(Key publicKey) throws Exception {
        SubjectPublicKeyInfo spki = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        StringWriter stringWriter = new StringWriter();
        try (JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter)) {
            pemWriter.writeObject(spki);
        }
        String pemString = stringWriter.toString();
        pemString = pemString.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""); // Remove any additional whitespaces
        return pemString;
    }

    private static void changeIndex() {
        int newIndex = (int) (Math.random() * 100);
        while (newIndex == index) {
            newIndex = (int) (Math.random() * 100);
        }
        index = newIndex;
    }

}
