package com.example.temipj.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //==========================sign up========================================

    SIGNUP_WRONG_MEMBERID(400, "MEMBER 아이디가 존재하지 않습니다."),
    SIGNUP_WRONG_LOGINID(400, "이메일 형식이 맞지 않습니다."),
    DUPLICATED_EMAIL(400,"중복된 이메일입니다."),
    SIGNUP_WRONG_PASSWORD(400, "비밀번호 형식이 맞지 않습니다."),
    ALREADY_SAVED_ID(409, "중복된 아이디입니다."),
    PASSWORDS_NOT_MATCHED(400,"비밀번호와 비밀번호가 일치하지 않습니다."),
    ALREADY_SAVED_MEMBERNAME(400, "중복된 이름입니다."),

    //=============================login=======================================
    MEMBER_NOT_FOUND(404,"사용자를 찾을 수 없습니다."),
    INVALID_MEMBER(404,"사용자를 찾을 수 없습니다."),
    LOGINID_EMPTY(400,"아이디를 입력해주세요."),
    PASSWORD_EMPTY(400,"비밀번호를 입력해주세요."),
    LOGINID_MISMATCH(404,"아이디가 일치하지 않습니다."),
    PASSWORD_MISMATCH(404,"비밀번호가 일치하지 않습니다."),
    MEMBER_WRONG_UPDATE(400,"이름과 비밀번호를 정확하게 입력해주세요."),
    MEMBER_WRONG_DELETE(400,"본인 아이디만 탈퇴 가능합니다."),

    //================================token========================================
    INVALID_TOKEN(404,"Token이 유효하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(401, "토근이 만료되었습니다. 다시 로그인 해주세요."),

    //==============================500 INTERNAL SERVER ERROR========================

    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 고객센터에 문의해주세요."),
    BIND_Fails(500,"서버 에러입니다. 고객센터에 문의해주세요."),
    NOT_VALUE_AT(500,"서버 에러입니다. 고객센터에 문의해주세요."),
    NO_ELEMENT(500,"서버 에러입니다. 고객센터에 문의해주세요.");


    private final int status;
    private final String message;
}
