package com.wwlei.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtil {
    private PasswordUtil() {}

    // 生成随机盐
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 生成盐和密码的哈希
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // 检查密码是否匹配
    public static boolean verifyPassword(String originalPassword, String storedPassword, String salt) throws NoSuchAlgorithmException {
        String hashedOriginal = hashPassword(originalPassword, salt);
        return hashedOriginal.equals(storedPassword);
    }
}
