package com.example.temipj.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfiguration {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowCredentials(true); // 내서버가 응답을 할 떄 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것

//        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://43.201.241.17:8080");
        config.addAllowedOrigin("http://localhost:9092");
//        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOriginPattern("*"); //모든 ip의 응답 허용
        config.addAllowedHeader("*"); //모든 header의 응답 허용
        config.addAllowedMethod("*"); //모든 get, post, put, delete, patch 요청을 허용
        config.addExposedHeader("*");
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/api/admins/**",config);
        source.registerCorsConfiguration("/api/**",config);
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);

    }
}
