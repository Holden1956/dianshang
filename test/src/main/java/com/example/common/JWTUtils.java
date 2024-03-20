package com.example.common;

import com.example.pojo.vo.UserLoginVO;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @author:xuanhe
 * @date:2024/2/28 10:43
 * @modyified By:
 */
public class JWTUtils {
    /*有效期*/
    private static final long TOKEN_EXPIRE = 7*24*60*60*1000;
    /*签名，需要定期更换*/
    private static final String TOKEN_SIGN="cqjtu_asd&&&";

    /**
     * 生成token
     * @param user， user中的token是空的
     * @return
     */
    public static String generateToken(UserLoginVO user){//为当前一次登录，生成一个token
        //Map<String,Object> map = new HashMap<>();
        //主体 subject ：有可能是用户，也有能是设备
        String subject = user.getUsername();
        //当前时间
        Date now = new Date();
        //租约到期时间
        long expireTime = now.getTime() + TOKEN_EXPIRE;
        //创建过期时间
        Date exp = new Date(expireTime);
        //生成token
        String token = Jwts.builder().setSubject(subject) //设置主体
                .setIssuedAt(now) //设置当前时间
                .setExpiration(exp) //设置过期时间
                .claim("id", user.getId()) // 设置payload : id
                .claim("username",user.getUsername()) // 用户名称
                //.addClaims(map)
                .signWith(SignatureAlgorithm.HS256, TOKEN_SIGN) //算法+签名
                .compact();
        return token;
    }


    public static Claims verifyToken(String token){//验证token
        //身份信息
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(TOKEN_SIGN)//设置签名
                    .parseClaimsJws(token) //设置解析的token
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ServiceException(ServiceCode.ERR_JWT_EXPIRED,"令牌过期");
        } catch (SignatureException e) {
            throw new ServiceException(ServiceCode.ERR_JWT_SIGNATURE,"令牌无效");
        } catch (Exception e) {
            throw new ServiceException(ServiceCode.ERR_JWT_MALFORMED,"令牌非法");
        }
        return claims;
    }
    /**
     * 登录成功后，每次发送请求，都要带上token，后台解析token
     * @param token
     * @return
     */
    public static UserLoginVO parseToken(String token){//解析当前登录用户的token
        Claims claims=verifyToken(token);
        //获取id
        Integer id = Integer.valueOf(claims.get("id").toString());
        //获取用户名称
        String username = claims.get("username").toString();
        UserLoginVO user = new UserLoginVO();
        user.setId(id);
        user.setUsername(username);
        return user;
    }
}
