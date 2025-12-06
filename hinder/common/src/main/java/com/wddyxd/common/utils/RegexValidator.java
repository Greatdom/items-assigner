package com.wddyxd.common.utils;


import java.util.regex.Pattern;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 13:26
 **/

public class RegexValidator {

    // -------------------------- 正则表达式常量 --------------------------
    /**
     * 手机号正则：支持13/14/15/16/17/18/19开头的11位数字（符合工信部最新号段）
     */
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

    /**
     * 邮箱正则：
     * - 用户名：字母、数字、下划线、减号、点（不能以点/减号开头/结尾，不能连续点/减号）
     * - 域名：字母、数字、减号、点（顶级域至少2位）
     */
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9]([\\w\\-\\.]*[a-zA-Z0-9])?@[a-zA-Z0-9]([\\w\\-]*[a-zA-Z0-9])?(\\.[a-zA-Z0-9]([\\w\\-]*[a-zA-Z0-9])?){1,}$";

    /**
     * 用户名正则：4-16位，仅字母（不能包含特殊字符）
     */
    private static final String USERNAME_REGEX = "^[a-zA-Z]{4,16}$";

    /**
     * 密码正则：4-16位，字母/数字/特殊字符（特殊字符包含!@#$%^&*()_+-=[]{}|;:,.<>?）
     */
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?]{4,16}$";

    // -------------------------- 预编译正则（提升性能） --------------------------
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    // -------------------------- 静态校验方法 --------------------------

    /**
     * 校验手机号
     * @param phone 手机号字符串
     * @return true=合法，false=非法
     */
    public static boolean validatePhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 校验邮箱
     * @param email 邮箱字符串
     * @return true=合法，false=非法
     */
    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 校验用户名（4-16位字母，无特殊字符）
     * @param username 用户名字符串
     * @return true=合法，false=非法
     */
    public static boolean validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * 校验密码（4-16位字母/数字/指定特殊字符）
     * @param password 密码字符串
     * @return true=合法，false=非法
     */
    public static boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}