package com.wddyxd.security.config;


import com.wddyxd.security.filter.TokenAuthFilter;
import com.wddyxd.security.filter.TokenLoginFilter;
import com.wddyxd.security.security.DefaultPasswordEncoder;
import com.wddyxd.security.security.TokenLogoutHandler;
import com.wddyxd.security.security.TokenManager;
import com.wddyxd.security.security.UnauthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @program: items-assigner
 * @description: spring-security总配置类
 * @author: wddyxd
 * @create: 2025-10-20 20:38
 **/

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig {

    private final TokenManager tokenManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final DefaultPasswordEncoder defaultPasswordEncoder;
    private final UserDetailsService userDetailsService;

    public TokenWebSecurityConfig(UserDetailsService userDetailsService,
                                  DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager,
                                  RedisTemplate<String, Object> redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置安全过滤器链
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                //.cors(cors -> {}) // 如果需要CORS，可以在这里配置

                // 异常处理
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(new UnauthEntryPoint())
                )
                // 授权配置
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(  "/user/auth/passwordSecurityGetter/**","/user/auth/login","/swagger-ui/**", "/v3/api-docs/**").permitAll() // 不进行认证的路径
                        .anyRequest().authenticated() // 其他所有请求都需要认证
                )
                // 退出登录配置
                .logout(logout -> logout
                        .logoutUrl("/user/auth/logout")
                        .addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate))
                )
                // 会话管理 - 无状态
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // 添加自定义过滤器
        http.addFilter(new TokenLoginFilter(authenticationManager, tokenManager, redisTemplate));
        http.addFilter(new TokenAuthFilter(authenticationManager, tokenManager, redisTemplate));

        return http.build();
    }



    /**
     * 配置认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration,
                                                       HttpSecurity http) throws Exception {
        // 首先通过 AuthenticationConfiguration 获取基础的 AuthenticationManager
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        // 然后配置 UserDetailsService 和 PasswordEncoder
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);

        return authenticationManager;
    }

}