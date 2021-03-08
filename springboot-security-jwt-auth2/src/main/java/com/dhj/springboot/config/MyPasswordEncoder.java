package com.dhj.springboot.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder
{
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    /**
     *
     * @param charSequence 表单填写的密码
     * @param s 从数据库中取得
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
