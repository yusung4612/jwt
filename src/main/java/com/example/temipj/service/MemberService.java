package com.example.temipj.service;


import com.example.temipj.domain.Member;
import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.LoginRequestDto;
import com.example.temipj.dto.requestDto.MemberRequestDto;
import com.example.temipj.dto.requestDto.TokenDto;
import com.example.temipj.dto.responseDto.MemberResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.MemberRepository;
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입
    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) throws IOException {

        //이메일 중복 체크
        if (null != isPresentMember(requestDto.getEmailId())) {
            return ResponseDto.fail(ErrorCode.ALREADY_SAVED_ID.name(),
                    ErrorCode.ALREADY_SAVED_ID.getMessage());
        }
        // 이메일 형식 체크
        if(!requestDto.getEmailId().contains("@")) {
            return ResponseDto.fail(ErrorCode.SIGNUP_WRONG_LOGINID.name(),
                    ErrorCode.SIGNUP_WRONG_LOGINID.getMessage());
        }

        // 사용자 이름 중복 체크
        if(null != isPresentMembername(requestDto.getMembername())) {
            return ResponseDto.fail(ErrorCode.ALREADY_SAVED_MEMBERNAME.name(),
                    ErrorCode.ALREADY_SAVED_MEMBERNAME.getMessage());
        }

        //패스워드 일치 체크
        if(!Objects.equals(requestDto.getPasswordConfirm(), requestDto.getPassword())){
            return ResponseDto.fail(ErrorCode.PASSWORDS_NOT_MATCHED.name(),
                    ErrorCode.PASSWORDS_NOT_MATCHED.getMessage());
        }

        Member member = Member.builder()
                .membername(requestDto.getMembername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .emailId(requestDto.getEmailId())
                .build();
        memberRepository.save(member);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .membername(member.getMembername())
                        .emailId(member.getEmailId())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

    // 로그인
    @Transactional
    public ResponseDto<?> loginMember(LoginRequestDto requestDto, HttpServletResponse response) {

        Member member = isPresentMember(requestDto.getEmailId());

        //null값 사용자 유효성 체크
        if (null == member) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(),
                    ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }
        //비밀번호 사용자 유효성 체크
        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail(ErrorCode.PASSWORD_MISMATCH.name(), ErrorCode.PASSWORD_MISMATCH.getMessage());
        }
        //인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .membername(member.getMembername())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .emailId(member.getEmailId())
                        .build()
        );
    }

    //로그아웃
    @Transactional
    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER.name(), ErrorCode.INVALID_MEMBER.getMessage());
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();

        if (null == member) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(),
                    ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }
        return tokenProvider.deleteRefreshToken(member);
    }

    //회원탈퇴
    @Transactional
    public ResponseDto<?> deleteMember(Long memberId, UserDetailsImpl userDetails) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () ->new IllegalArgumentException("등록되지 않은 회원입니다.")
        );
        if(!member.equals(userDetails.getMember())){
            return ResponseDto.fail(ErrorCode.MEMBER_WRONG_DELETE.name(), ErrorCode.MEMBER_WRONG_DELETE.getMessage());
        }
        refreshTokenRepository.deleteByMemberId(memberId);
        memberRepository.deleteById(memberId);

        return ResponseDto.success("회원 탈퇴가 완료되었습니다.");
    }


    // 사용자 이름 인증
    @Transactional
    public Object isPresentMembername(String membername) {
        Optional<Member> Member = memberRepository.findByMembername(membername);
        return Member.orElse(null);
    }

    //회원 이메일 유효성 인증
    @Transactional
    public Member isPresentMember(String emailId) {
        Optional<Member> Member = memberRepository.findByEmailId(emailId);
        return Member.orElse(null);
    }

    // 헤더에 담기는 토큰
    private void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh_Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    //토큰 재발급
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER.name(), ErrorCode.INVALID_MEMBER.getMessage());
        }

        Member member = refreshTokenRepository.findByValue(request.getHeader("Refresh_Token")).get().getMember();

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .membername(member.getMembername())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .emailId(member.getEmailId())
                        .build()
        );
    }

}
