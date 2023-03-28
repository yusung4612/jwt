package com.example.temipj.controller;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.LoginRequestDto;
import com.example.temipj.dto.requestDto.AdminRequestDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/admins")
public class AdminController {

    private final AdminService adminService;

    //회원가입
    @PostMapping(value="/signup")
    @ResponseBody
    public ResponseDto<?> signup(@RequestBody @Valid AdminRequestDto requestDto) throws IOException {
        return adminService.createAdmin(requestDto);
    }

    //로그인
    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
        return adminService.loginAdmin(requestDto, response);
    }

    //로그아웃
    @PostMapping(value = "/logout")
    @ResponseBody
    public ResponseDto<?> logout(HttpServletRequest request) {
        return adminService.logout(request);
    }

    //토큰재발급
    @PostMapping(value = "/reissue")
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return adminService.reissue(request, response);
    }

    //회원탈퇴
    @DeleteMapping(value="/delete/{adminId}")
    @ResponseBody
    public ResponseDto<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.deleteAdmin(id, userDetails);
    }

//    @RequestMapping(value = "/signup", method = RequestMethod.GET)
//    public String handleGetRequest() {
//        throw new UnsupportedOperationException("GET 요청은 지원하지 않습니다.");
//    }

    //이메일 중복 확인

    //이메일 인증
}
