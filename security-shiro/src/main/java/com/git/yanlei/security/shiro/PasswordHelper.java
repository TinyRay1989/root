package com.git.yanlei.security.shiro;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.git.yanlei.security.shiro.entity.User;

public class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public static final String ALGORITHM_NAME = "md5";
    public static final int HASH_ITERATIONS = 2;

    public static void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String credentialsSalt = user.getCredentialsSalt();
        String newPassword = encryptAndGetPassword(user.getPassword(), credentialsSalt);
        user.setPassword(newPassword);
    }
    
    public static String encryptAndGetPassword(String password, String salt){
        String newPassword = new SimpleHash(
                ALGORITHM_NAME,
                password,
                ByteSource.Util.bytes(salt),
                HASH_ITERATIONS).toHex();
        return newPassword;
    }
}
