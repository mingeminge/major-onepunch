package com.yzm.majorserver.core.security;

import com.yzm.majorserver.common.utils.IpAddressUtil;
import com.yzm.majorserver.common.utils.RedisUtil;
import com.yzm.majorserver.common.vo.IpAddressResponse;
import com.yzm.majorserver.web.service.UserServiceImpl;
import com.yzm.majorserver.web.service.VisitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author : yizuomin
 * @date : Created in 16:04 2019/4/11
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthLogoutSuccessHandler logoutSuccessHandler;
    private UserServiceImpl userDetailService;
    private AuthProvider authProvider;
    private RedisUtil redisUtil;
    private VisitServiceImpl visitService;
    private IpAddressUtil ipAddressUtil;

    private AuthPermissionEvaluator permissionEvaluator;

    @Autowired
    public SecurityConfig(AuthLogoutSuccessHandler logoutSuccessHandler, UserServiceImpl userDetailService, AuthProvider authProvider, RedisUtil redisUtil,
                          VisitServiceImpl visitService, AuthPermissionEvaluator permissionEvaluator, IpAddressUtil ipAddressUtil) {
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userDetailService = userDetailService;
        this.authProvider = authProvider;
        this.redisUtil = redisUtil;
        this.visitService = visitService;
        this.permissionEvaluator = permissionEvaluator;
        this.ipAddressUtil = ipAddressUtil;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler myWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        return handler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/notLogin").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login").loginPage("/notLogin")
                .and()
                .exceptionHandling()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .cors()
                .and()
                .csrf().disable()
                .addFilter(new AuthTokenFilter(authenticationManager(), redisUtil))
                .addFilterAt(new AuthFilter(authenticationManagerBean(), redisUtil, visitService, ipAddressUtil), UsernamePasswordAuthenticationFilter.class);
    }
}
