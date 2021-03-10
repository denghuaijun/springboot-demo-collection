package com.dhj.springboot.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dhj.springboot.bean.SysUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * JWT工具类
 */
public class JwtUtils {
    /**
     * 签名key
     */
    private static final String SIGN_KEY="ABCDEFGxyzSignKey";
    /**
     * 设置JWT声明
     */
    private static final String SUB_JECT="subject";
    /**
     * 设置过期时间Claims claims = Jwts.claims().setExpiration(new Date(System.currentTimeMillis() + 3600000));
     * 1h
     */
    private static final Long EXPIRATION=60*60*1000L;

    /**
     * 纯手写JWT
     * @return
     */
    public static String customJwt(){
        //1、定义请求头
        JSONObject headJson = new JSONObject();
        headJson.put("alg","HS256");
        headJson.put("typ","JWT");
        //2、定义payload存放数据，若存放的数据是敏感的，可以在里面继续使用对称加密之后再放入
        JSONObject payloadJson = new JSONObject();
        payloadJson.put("userName","denghuaijun");
        payloadJson.put("phone","151****5359");
        //分别对头及payload进行base64编码
        String encodeHeader = Base64.getEncoder().encodeToString(headJson.toJSONString().getBytes());
        String encodePayload = Base64.getEncoder().encodeToString(payloadJson.toJSONString().getBytes());
        //生成sign
        String sign = DigestUtils.md5Hex(payloadJson.toJSONString() + SIGN_KEY);
        return encodeHeader+"."+encodePayload+"."+sign;
    }

    /**
     * 验证签名
     * @param jwtToken
     * @return
     */
    public static boolean verifiedSignature(String jwtToken){
        if (StringUtils.isEmpty(jwtToken)){
            return false;
        }
        String[] jwtArr = jwtToken.split("\\.");
        if (jwtArr !=null && jwtArr.length>0){
            //获取base64编码的payload然后进行解码
            byte[] decode = Base64.getDecoder().decode(jwtArr[1]);
            String decodePayLoad=new String(decode);
            return DigestUtils.md5Hex(decodePayLoad+SIGN_KEY).equals(jwtArr[2]);
        }
        return false;
    }
    //***************************后面为java操作JWT************************************************

    /**
     * 根据用户信息生成对应的jwtToken
     * @param userDetails
     * @return
     */
    public static String createJWTtoken(SysUserDetails userDetails){
        String jwt = Jwts.builder()
                .setSubject(SUB_JECT)
                .claim("userName", userDetails.getUsername())
                .claim("permissions", userDetails.getGrantedAuthorityList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
                .compact();
        return jwt;
    }
    /**
     * 根据jwt得到解析的body体
     * @param jwtToken
     * @return
     */
    public static Claims getClaimsByToken(String jwtToken){
        Claims claims=null;
        try{
             claims = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(jwtToken).getBody();
        }catch (Exception e){
            return null;
        }
        return claims;
    }

    /**
     * 根据jwt得到用户名
     * @param jwtToken
     * @return
     */
    public static String getUserName(String jwtToken){
        Claims body = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(jwtToken).getBody();
        return body.get("userName").toString();
    }
    /**
     * 根据jwt获取用户角色
     * @param jwtToken
     * @return
     */
    public static List<SimpleGrantedAuthority> getAuthoris(String jwtToken){
        Claims body = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(jwtToken).getBody();
        List permissions = (List) body.get("permissions");
        String jsonString = JSONArray.toJSONString(permissions);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = JSONArray.parseArray(jsonString, SimpleGrantedAuthority.class);
        return simpleGrantedAuthorities;
    }
    public static void main(String[] args) {
//        String jwt = customJwt();
//        boolean b = verifiedSignature(jwt);
       // System.out.println("jwt==:"+ createJWTtoken());
       // System.out.println(b);
    }
}
