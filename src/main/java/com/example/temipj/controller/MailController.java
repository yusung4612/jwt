//package com.example.temipj.controller;
//
//import com.example.temipj.dto.responseDto.ResponseDto;
//import com.example.temipj.service.MailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class MailController {
//
//    private final MailService mailService;
//    private final Validation validation;
//
//    // 인증번호 발송하는 api
//    @PostMapping("/api/mail/auth")
//    public ResponseDto<?> mailAuth(@RequestBody EmailAuthRequestDto requestDto) throws Exception {
//        validation.validateEmailInput(requestDto);
//        validation.emailCheck(requestDto);
//        return mailService.sendSimpleMessage(requestDto.getEmail());
//    }
//
//    // 인증번호 작성 후 확인 버튼
//    @PostMapping("/api/mail/confirm")
//    public ResponseDto<?> mailConfirm(@RequestBody EmailAuthRequestDto requestDto){
//        return mailService.mailConfirm(requestDto);
//    }
//}