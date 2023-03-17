package com.example.temipj.controller;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.LoginRequestDto;
import com.example.temipj.dto.requestDto.MemberRequestDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/members")
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping(value="/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) throws IOException {
        return memberService.createMember(requestDto);
    }

    //로그인
    @PostMapping(value = "/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.loginMember(requestDto, response);
    }

    //로그아웃
    @PostMapping(value = "/logout")
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }

    //토큰재발급
    @PostMapping(value = "/reissue")
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return memberService.reissue(request, response);
    }

    //회원탈퇴
    @DeleteMapping(value="/delete/{memberId}")
    public ResponseDto<?> delete(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.deleteMember(memberId, userDetails);
    }

    //이메일 중복 확인

    //이메일 인증
}
