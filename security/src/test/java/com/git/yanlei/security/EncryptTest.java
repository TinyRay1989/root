package com.git.yanlei.security;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptTest {
    private String str = "hello";

    private static final Logger log = LoggerFactory.getLogger(EncryptTest.class);
    @Test
    public void base64() throws UnsupportedEncodingException {
        log("base64");
        String encodeStr = org.apache.commons.codec.binary.Base64.encodeBase64String(str
                .getBytes("utf-8"));
        String decodeStr = new String(
                org.apache.commons.codec.binary.Base64.decodeBase64(encodeStr));
        log(encodeStr);
        log(decodeStr);

        String encodeStr1 = Base64.encodeToString(str.getBytes("utf-8"));
        String decodeStr1 = new String(Base64.decode(encodeStr));
        log(encodeStr1);
        log(decodeStr1);
    }

    @Test
    public void hexadecimal() throws UnsupportedEncodingException {
        log("hex");
        String encodeStr = Hex.encodeToString(str.getBytes("utf-8"));
        String decodeStr = new String(Hex.decode(encodeStr));
        log(encodeStr);
        log(decodeStr);
    }

    private void log(String msg) {
        log.info(msg);
    }

    @Test
    public void hash() {
        log("encrypt");
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();
        String SHA256 = new Sha256Hash(str, salt).toString();
        String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
        log(md5);
        log(SHA256);
        log(simpleHash);
    }

    @Test
    public void hashService() {
        log("hashService");
        DefaultHashService service = new DefaultHashService();
        service.setHashAlgorithmName("SHA-512");
        service.setPrivateSalt(ByteSource.Util.bytes("123"));
        service.setGeneratePublicSalt(true);
        service.setRandomNumberGenerator(new SecureRandomNumberGenerator());
        service.setHashIterations(2);
        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5")
                .setSource(ByteSource.Util.bytes("123"))
                .setIterations(2)
                .build();
        String result = service.computeHash(request).toString();
        log.info(result);
        
        
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        String hex = randomNumberGenerator.nextBytes().toHex();
        log.info(hex);
    }
    
    @Test
    public void encrypt(){
        AesCipherService service = new AesCipherService();
        service.setKeySize(128);
        Key key = service.generateNewKey();
        String text = "123";
        ByteSource encrypt = service.encrypt(text.getBytes(), key.getEncoded());
        ByteSource decrypt = service.decrypt(encrypt.getBytes(), key.getEncoded());
        Assert.assertEquals(text, new String(decrypt.getBytes()));
    }
}
