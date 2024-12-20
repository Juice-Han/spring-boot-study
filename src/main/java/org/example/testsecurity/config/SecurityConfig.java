package org.example.testsecurity.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login","/loginProc","/join","joinProc", "loginFail").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // static 자원에 대한 접근 모두 허용
                        .requestMatchers("/api/admin/**","/admin/**").hasRole("ADMIN")
                        .requestMatchers("/logout").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        // rest api 요청을 할 경우 csrf 검증 비활성화
        http
                .csrf((auth) -> auth
                        .ignoringRequestMatchers("/api/**")
                );

        http
                .formLogin((auth) -> auth
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .failureForwardUrl("/loginFail")
                        .defaultSuccessUrl("/articles", true)
                        .permitAll()
                );

        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                );

        // 중복 로그인 처리하는 과정
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));

        // 세션 고정 공격을 방어하기 위한 방법 - 공격자의 세션 id로 로그인 해도 새로운 세션 id가 발급되어
        // 공격자의 세션 id는 여전히 익명 사용자 세션이 됨
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
