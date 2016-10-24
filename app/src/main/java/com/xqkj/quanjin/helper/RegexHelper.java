package com.xqkj.quanjin.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
    /**
     * 使用正则表达式匹配手机号
     *
     * @param cellPhoneNumber
     * @return
     */
    public static boolean isMobile(String cellPhoneNumber) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(cellPhoneNumber);
        b = m.matches();
        return b;
    }

    /**
     * 使用正则表达式匹配邮箱地址
     *
     * @param email
     * @return
     */
    public static boolean matchEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean matchPhone(String phone) {
        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 匹配用户登录/注册密码
     *
     * @param loginPassword
     * @return
     */
    public static boolean matchLoginPassword(String loginPassword) {
        Pattern pattern = Pattern.compile("^\\w{6,}$");
        Matcher matcher = pattern.matcher(loginPassword);
        return matcher.matches();
    }
}
