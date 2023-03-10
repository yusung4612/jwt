package com.example.temipj.dto.requestDto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TokenDto { //서버에서 발급받은 토큰 정보를 저장

    private String grantType; //토큰 타입, 보통 Bearer로 저장됨

    private String accessToken; //인증에 성공하면 발급받는 접근 토큰 정보

    private String refreshToken; //인증에 성공하면 발급받은 새로운 토큰을 발급받기 위한 리프레시 토큰 정보

    private Long accessTokenExpiresIn; //토큰 만료시간 정보

    // 토큰에 담기는 값
    public void tokenToHeaders(HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + getAccessToken()); //엑세스 토큰
        response.addHeader("Refresh_Token", getRefreshToken()); //리프레시 토큰
        response.addHeader("Access-Token-Expire-Time", getAccessTokenExpiresIn().toString()); //엑세스토큰 만료시간
    }

}