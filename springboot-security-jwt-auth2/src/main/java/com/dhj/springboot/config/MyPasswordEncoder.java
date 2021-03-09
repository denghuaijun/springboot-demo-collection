package com.dhj.springboot.config;

import com.dhj.demo.business.common.utils.MD5UtilSalt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder
{
    /**
     * 进行对密码MD5加密
     * @param charSequence
     * @return
     */
    @Override
    public String encode(CharSequence charSequence) {
        return MD5UtilSalt.encode(charSequence.toString());
    }

    /**
     *
     * @param charSequence 用户通过表单填写的密码
     * @param s 从数据库中取得加密的密码
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(MD5UtilSalt.encode(charSequence.toString()));
    }
}
