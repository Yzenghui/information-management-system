package com.ims.backend.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT (JSON Web Token) 工具类。
 * 负责生成和验证用户登录后的访问令牌，是实现无状态认证的核心。
 */
@Component // 声明为Spring组件，方便在其他地方通过@Autowired注入
public class JwtUtil {

    /**
     * 用于生成签名的密钥字节数组。
     * 【安全警告】此处的密钥为示例，硬编码在代码中极不安全！
     * 生产环境中必须通过环境变量、配置服务器等安全方式注入，
     * 且长度必须足够（HS512算法要求>=512位，即64字符以上）。
     */
    private static final byte[] KEY_BYTES = "YourSuperLongAndSecureSecretKeyThatIsAtLeast64CharactersLongForHS512Algorithm!!!".getBytes();

    /**
     * 根据密钥字节数组生成的、符合HS512算法要求的安全密钥对象。
     */
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY_BYTES);

    /**
     * 令牌有效期（毫秒）。这里设置为2小时。
     */
    private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000;

    /**
     * 为指定用户生成JWT访问令牌。
     *
     * @param username 用户名，将作为令牌的“主题”(subject)
     * @param userId   用户ID，将作为自定义声明(claim)存入令牌负载
     * @param role     用户角色，将作为自定义声明存入令牌负载
     * @return 生成的JWT令牌字符串（格式：Header.Payload.Signature）
     */
    public String generateToken(String username, Integer userId,String role) {
        // 创建自定义声明（Claims）Map，用于存放业务数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId); // 将用户ID存入令牌，后续可直接取出，无需查库
        claims.put("role", role);

        // 使用Jwts构建器流畅地创建令牌
        return Jwts.builder()
                .claims(claims)               // 设置自定义声明（负载中的数据）
                .subject(username)            // 设置令牌主题，通常用于标识用户
                .issuedAt(new Date())         // 设置签发时间（iat）
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间（exp）
                .signWith(SECRET_KEY, Jwts.SIG.HS512) // 使用密钥和指定算法进行签名，防止篡改
                .compact(); // 生成最终的、经过Base64Url编码的JWT字符串
    }

    /**
     * 从 Token 中解析用户名。
     *
     * @param token JWT 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)          // 使用密钥验证签名
                .build()                         // 构建解析器实例
                .parseSignedClaims(token)        // 解析并验证JWT的签名和结构
                .getPayload()                    // 获取JWT的有效载荷（Payload）
                .getSubject();                   // 提取主题（通常是用户名）
    }

    /**
     * 从 Token 中解析用户 ID。
     *
     * @param token JWT 令牌
     * @return 用户 ID
     */
    public Integer getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)          // 使用密钥验证签名
                .build()                         // 构建解析器实例
                .parseSignedClaims(token)        // 解析并验证JWT的签名和结构
                .getPayload()                    // 获取JWT的有效载荷（Payload）
                .get("userId", Integer.class);   // 从自定义声明中提取用户ID，并转换为Integer类型
    }

    /**
     * 从 Token 中解析用户角色。
     *
     * @param token JWT 令牌
     * @return 用户角色
     */
    public String getRoleFromToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    /**
     * 验证 Token 是否有效。
     *
     * @param token JWT 令牌
     * @return true 表示有效，false 表示无效或过期
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)      // 使用密钥验证签名
                    .build()                     // 构建解析器实例
                    .parseSignedClaims(token);   // 解析并验证JWT（包括签名、过期时间等）
            return true;                         // 无异常则表示令牌有效
        } catch (Exception e) {
            return false;                        // 捕获任何异常（如签名错误、过期、格式非法等）视为无效
        }
    }
}