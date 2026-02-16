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
     * @return 生成的JWT令牌字符串（格式：Header.Payload.Signature）
     */
    public String generateToken(String username, Integer userId) {
        // 创建自定义声明（Claims）Map，用于存放业务数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId); // 将用户ID存入令牌，后续可直接取出，无需查库

        // 使用Jwts构建器流畅地创建令牌
        return Jwts.builder()
                .claims(claims)               // 设置自定义声明（负载中的数据）
                .subject(username)            // 设置令牌主题，通常用于标识用户
                .issuedAt(new Date())         // 设置签发时间（iat）
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间（exp）
                .signWith(SECRET_KEY, Jwts.SIG.HS512) // 使用密钥和指定算法进行签名，防止篡改
                .compact(); // 生成最终的、经过Base64Url编码的JWT字符串
    }

    // 注意：此处省略了验证令牌(validateToken)、解析用户ID(getUserIdFromToken)等方法。
    // 这些方法将在实现鉴权拦截器(JwtAuthenticationFilter)时补充。
}