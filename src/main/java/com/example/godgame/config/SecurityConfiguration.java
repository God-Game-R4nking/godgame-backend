package com.example.godgame.config;

import com.example.godgame.auth.filter.JwtVerificationFilter;
import com.example.godgame.auth.handler.MemberAccessDeniedHandler;
import com.example.godgame.auth.handler.MemberAuthenticationEntryPoint;
import com.example.godgame.auth.handler.MemberAuthenticationFailureHandler;
import com.example.godgame.auth.handler.MemberAuthenticationSuccessHandler;
import com.example.godgame.auth.jwt.JwtTokenizer;
import com.example.godgame.auth.utils.JwtAuthorityUtils;
import com.example.godgame.auth.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final JwtAuthorityUtils authorityUtils;

    // 검증 객체에 전달하기 위해 RedisTemplate DI
    private final RedisTemplate<String, Object> redisTemplate;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, JwtAuthorityUtils authorityUtils, RedisTemplate<String, Object> redisTemplate) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/members").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/members/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/members").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.GET, "/members/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/game-rooms/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/authenticate").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/boards").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/comments").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/boards").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.GET, "/boards/gets").permitAll()
                        .antMatchers(HttpMethod.POST, "/game-rooms/*/start").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }

    public class CustomFilterConfigurer  extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception{
            CharacterEncodingFilter filter = new CharacterEncodingFilter();
            filter.setEncoding("UTF-8");
            builder.addFilterBefore(filter, CsrfFilter.class);
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());
            JwtVerificationFilter jwtVerificationFilter =
                    new JwtVerificationFilter(jwtTokenizer,authorityUtils,redisTemplate);
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
//                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }
}
