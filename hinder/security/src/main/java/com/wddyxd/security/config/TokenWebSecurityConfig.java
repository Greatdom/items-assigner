package com.wddyxd.security.config;


import com.wddyxd.security.filter.TokenAuthFilter;
import com.wddyxd.security.filter.TokenLoginFilter;
import com.wddyxd.security.provider.EmailCodeAuthenticationProvider;
import com.wddyxd.security.provider.PasswordAuthenticationProvider;
import com.wddyxd.security.provider.PhoneCodeAuthenticationProvider;
import com.wddyxd.security.security.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

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

    private final UserInfoManager userInfoManager;
    private final UserTokenManager userTokenManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final DefaultPasswordEncoder defaultPasswordEncoder;
    private final UserDetailsService passwordUserDetailsService;
    private final UserDetailsService phoneCodeUserDetailsService;
    private final UserDetailsService emailCodeUserDetailsService;

    public TokenWebSecurityConfig(
            @Qualifier("passwordUserDetailsService") UserDetailsService passwordUserDetailsService,
            @Qualifier("phoneCodeUserDetailsService") UserDetailsService phoneCodeUserDetailsService,
            @Qualifier("emailCodeUserDetailsService") UserDetailsService emailCodeUserDetailsService,
                                  DefaultPasswordEncoder defaultPasswordEncoder,
                                  UserTokenManager userTokenManager,
                                  RedisTemplate<String, Object> redisTemplate,
                                    UserInfoManager userInfoManager) {
        this.passwordUserDetailsService = passwordUserDetailsService;
        this.phoneCodeUserDetailsService = phoneCodeUserDetailsService;
        this.emailCodeUserDetailsService = emailCodeUserDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.userTokenManager = userTokenManager;
        this.redisTemplate = redisTemplate;
        this.userInfoManager = userInfoManager;
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
                        exception.authenticationEntryPoint(new UnAuthEntryPoint())
                )
                // 授权配置
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(  "/user/auth/passwordSecurityGetter/**","/user/auth/login","/swagger-ui/**", "/v3/api-docs/**").permitAll() // 不进行认证的路径
                        .anyRequest().authenticated() // 其他所有请求都需要认证
                )
                // 退出登录配置
                .logout(logout -> logout
                        .logoutUrl("/user/auth/logout")
                        .addLogoutHandler(new TokenLogoutHandler(userTokenManager, redisTemplate))
                )
                // 会话管理 - 无状态
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // 添加自定义过滤器
        http.addFilter(new TokenLoginFilter(authenticationManager, userTokenManager, redisTemplate, userInfoManager));
        http.addFilter(new TokenAuthFilter(authenticationManager, userTokenManager, redisTemplate, userInfoManager));

        return http.build();
    }


    /**
     * 配置认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        PasswordAuthenticationProvider passwordAuthenticationProvider =
                new PasswordAuthenticationProvider(passwordUserDetailsService, defaultPasswordEncoder);
        PhoneCodeAuthenticationProvider phoneAuthenticationProvider =
                new PhoneCodeAuthenticationProvider(phoneCodeUserDetailsService, defaultPasswordEncoder);
        EmailCodeAuthenticationProvider emailAuthenticationProvider =
                new EmailCodeAuthenticationProvider(emailCodeUserDetailsService, defaultPasswordEncoder);
        return new ProviderManager(
                Arrays.asList(passwordAuthenticationProvider, phoneAuthenticationProvider, emailAuthenticationProvider)
        );
    }
}