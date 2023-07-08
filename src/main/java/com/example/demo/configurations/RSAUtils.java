package com.example.demo.configurations;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import javax.crypto.Cipher;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class RSAUtils implements InitializingBean {
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    @Override
    public void afterPropertiesSet() throws NoSuchAlgorithmException {
        generateKeyPair();
    }

    private void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        privateKey = kp.getPrivate();
        publicKey = kp.getPublic();
    }

    public static String getPublicKey() {
        String key = "";
        try {
            key = convertToPEM(publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static String decrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
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

}
