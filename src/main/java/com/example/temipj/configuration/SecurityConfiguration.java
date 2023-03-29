package com.example.temipj.configuration;


import com.example.temipj.jwt.AccessDeniedHandlerException;
import com.example.temipj.jwt.AuthenticationEntryPointException;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.service.UserDetailsServiceImpl;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Security 활성화
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)

public class SecurityConfiguration {

    @Value("${jwt.secret}")
    String SECRET_KEY;
    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPointException authenticationEntryPointException;
    private final AccessDeniedHandlerException accessDeniedHandlerException;
    private final CorsConfiguration corsConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] AUTH_WHITELIST = {
//            "/v1/members/**", "/v1/auth/**", "/v1/member/emailcheck",
            "/api/admins/**","/api/employees/**", "/" ,"/**"
    };

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers("/h2-console/**", "/favicon.ico") // h2 database 테스트가 원활하도록 관련 API 들은 전부 무시
//                .requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations())) //정적 파일(css, 이미지, ... 등)
//                ;
//    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();

        http.addFilter(corsConfiguration.corsFilter());
        http.csrf().disable() // csrf 보안토큰 disable 처리
//                .httpBasic().disable()
//                .addFilterBefore(new JwtFilter(SECRET_KEY, tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                )

                //exception handling 할 때 직접 만든 class를 추가
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointException)
                .accessDeniedHandler(accessDeniedHandlerException)

                //Security는 기본적으로 세션을 사용
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Token 기반 인증이므로 세션 설정을 Stateless로 설정

        http.authorizeHttpRequests(authorize -> authorize //요청에 대한 사용권한 설정 //로그인, 회원가입 Api는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/api/**").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/admins/**").permitAll()
                        .requestMatchers("/", "/**").permitAll()
                        .requestMatchers("/v2/api-docs",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/api/admins/**",
                                "/",
                                "/**",
                                "/api/employees/**",
                                "/api/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**").permitAll()

                        .anyRequest().authenticated() // 나머지 API는 전부 인증 필요

                        //JwtFilter를 addFilterBefore로 등록 했던 JwtSecurityConfig 클래스를 적용
                        .and()
                        .addFilter(corsConfiguration.corsFilter()))

                .apply(new JwtSecurityConfiguration(SECRET_KEY, tokenProvider, userDetailsService));
        return http.build();
    }
}