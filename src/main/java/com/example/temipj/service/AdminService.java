package com.example.temipj.service;


import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.LoginRequestDto;
import com.example.temipj.dto.requestDto.AdminRequestDto;
import com.example.temipj.dto.requestDto.TokenDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.dto.responseDto.AdminResponseDto;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.AdminRepository;
import com.example.temipj.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입
    @Transactional
    public ResponseDto<?> createAdmin(AdminRequestDto requestDto) throws IOException {

        // 이메일 중복 체크
        if (null != isPresentAdmin(requestDto.getEmailId())) {
            return ResponseDto.fail(ErrorCode.ALREADY_SAVED_ID.name(),
                    ErrorCode.ALREADY_SAVED_ID.getMessage());
        }
        // 이메일 형식 체크
        if(!requestDto.getEmailId().contains("@")) {
            return ResponseDto.fail(ErrorCode.SIGNUP_WRONG_LOGINID.name(),
                    ErrorCode.SIGNUP_WRONG_LOGINID.getMessage());
        }

        // 사용자 이름 중복 체크
        if(null != isPresentAdminName(requestDto.getAdminName())) {
            return ResponseDto.fail(ErrorCode.ALREADY_SAVED_ADMIN_NAME.name(),
                    ErrorCode.ALREADY_SAVED_ADMIN_NAME.getMessage());
        }

        // 패스워드 일치 체크
        if(!Objects.equals(requestDto.getPassword(), requestDto.getPasswordConfirm())){
            return ResponseDto.fail(ErrorCode.PASSWORDS_NOT_MATCHED.name(),
                    ErrorCode.PASSWORDS_NOT_MATCHED.getMessage());
        }

        Admin admin = Admin.builder()
                .adminName(requestDto.getAdminName())
                .emailId(requestDto.getEmailId())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .passwordConfirm(passwordEncoder.encode((requestDto.getPasswordConfirm())))
                .build();
        adminRepository.save(admin);

        return ResponseDto.success(
                AdminResponseDto.builder()
                        .id(admin.getId())
                        .adminName(admin.getAdminName())
                        .emailId(admin.getEmailId())
                        .createdAt(admin.getCreatedAt())
                        .modifiedAt(admin.getModifiedAt())
                        .build()
        );
    }

    // 로그인
    @Transactional
    public ResponseDto<?> loginAdmin(LoginRequestDto requestDto, HttpServletResponse response) {

        Admin admin = isPresentAdmin(requestDto.getEmailId());

        // null값 사용자 유효성 체크
        if (null == admin) {
            return ResponseDto.fail(ErrorCode.ADMIN_NOT_FOUND.name(),
                    ErrorCode.ADMIN_NOT_FOUND.getMessage());
        }
        // 비밀번호 사용자 유효성 체크
        if (!admin.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail(ErrorCode.PASSWORD_MISMATCH.name(), ErrorCode.PASSWORD_MISMATCH.getMessage());
        }
        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(admin);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                AdminResponseDto.builder()
                        .id(admin.getId())
                        .adminName(admin.getAdminName())
                        .createdAt(admin.getCreatedAt())
                        .modifiedAt(admin.getModifiedAt())
                        .emailId(admin.getEmailId())
                        .accessToken(tokenDto.getAccessToken())
                        .build()
        );
    }

    // 로그아웃
    @Transactional
    public ResponseDto<?> logout(HttpServletRequest request) {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
        //        if (!tokenProvider.validateToken(request.getHeader("Authorization"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_ADMIN.name(), ErrorCode.INVALID_ADMIN.getMessage());
//        }
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();

        if (null == admin) {
            return ResponseDto.fail(ErrorCode.ADMIN_NOT_FOUND.name(),
                    ErrorCode.ADMIN_NOT_FOUND.getMessage());
        }
        return tokenProvider.deleteRefreshToken(admin);
    }

    // 회원탈퇴
    @Transactional
    public ResponseDto<?> deleteAdmin(Long adminId, UserDetailsImpl userDetails) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () ->new IllegalArgumentException("등록되지 않은 회원입니다.")
        );
//        if(!admin.equals(userDetails.getAdmin())){
//            return ResponseDto.fail(ErrorCode.ADMIN_WRONG_DELETE.name(), ErrorCode.ADMIN_WRONG_DELETE.getMessage());
//        }
        refreshTokenRepository.deleteByAdminId(adminId);

        adminRepository.deleteById(adminId);

        return ResponseDto.success("회원 탈퇴가 완료되었습니다.");
    }


    // 사용자 이름 인증
    @Transactional
    public Object isPresentAdminName(String adminName) {
        Optional<Admin> Admin = adminRepository.findByAdminName(adminName);
        return Admin.orElse(null);
    }

    // 회원 이메일 유효성 인증
    @Transactional
    public Admin isPresentAdmin(String emailId) {
        Optional<Admin> Admin = adminRepository.findByEmailId(emailId);
        return Admin.orElse(null);
    }

    // Header에 담기는 Token
    private void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh_Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    // 토큰 재발급
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_ADMIN.name(), ErrorCode.INVALID_ADMIN.getMessage());
        }

        Admin admin = refreshTokenRepository.findByValue(request.getHeader("Refresh_Token")).get().getAdmin();

        TokenDto tokenDto = tokenProvider.generateTokenDto(admin);
        tokenToHeaders(tokenDto, response);
        return ResponseDto.success(
                AdminResponseDto.builder()
                        .id(admin.getId())
                        .adminName(admin.getAdminName())
                        .createdAt(admin.getCreatedAt())
                        .modifiedAt(admin.getModifiedAt())
                        .emailId(admin.getEmailId())
                        .build()
        );
    }

}
