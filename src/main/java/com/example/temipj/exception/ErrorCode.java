package com.example.temipj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //==========================sign up========================================
    OK(300, "응답이 정상 처리 되었습니다."),
    SIGNUP_WRONG_ADMINID(400, "ADMIN 아이디가 존재하지 않습니다."),
    SIGNUP_WRONG_LOGINID(400, "이메일 형식이 맞지 않습니다."),
    DUPLICATED_EMAIL(400,"중복된 이메일입니다."),
    EMAIL_NOT_FOUND(400, "해당 이메일을 찾을 수 없습니다."),
    SIGNUP_WRONG_PASSWORD(400, "비밀번호 형식이 맞지 않습니다."),
    ALREADY_SAVED_ID(400, "중복된 아이디입니다."),
    ALREADY_SAVED_ADMIN_NAME(400, "중복된 이름입니다."),
    PASSWORDS_NOT_MATCHED(400,"비밀번호와 비밀번호가 일치하지 않습니다."),
    ALREADY_SAVED_ADMIN(400, "중복된 이름입니다."),

    //=============================login=======================================
    ADMIN_NOT_FOUND(404,"사용자를 찾을 수 없습니다."),
    INVALID_ADMIN(404,"사용자를 찾을 수 없습니다."),
    LOGINID_EMPTY(400,"아이디를 입력해주세요."),
    PASSWORD_EMPTY(400,"비밀번호를 입력해주세요."),
    LOGINID_MISMATCH(404,"아이디가 일치하지 않습니다."),
    PASSWORD_MISMATCH(404,"비밀번호가 일치하지 않습니다."),
    ADMIN_WRONG_UPDATE(400,"이름과 비밀번호를 정확하게 입력해주세요."),
    ADMIN_WRONG_DELETE(400,"본인 아이디만 탈퇴 가능합니다."),
    ADMIN_UPDATE_WRONG_ACCESS(400,"잘못된 접근입니다."),

    //================================team========================================
    NOT_BLANK_TEAM(400,"공백은 허용되지 않습니다."),
    NOT_EXIST_TEAM(404, "존재하지 않는 상위부서 입니다."),
    TEAM_UPDATE_WRONG_ACCESS(400, "잘못된 접근입니다."),
    TEAM_DELETE_WRONG_ACCESS(400, "잘못된 접근입니다."),

    //==============================division====================================
    DIVISION_NOT_FOUND(404,"상위부서를 찾을 수 없습니다."),
    NOT_EXIST_DIVISION(404, "존재하지 않는 상위부서 입니다."),

    //==============================department====================================
    DEPARTMENT_NOT_FOUND(404,"하위부서를 찾을 수 없습니다."),
    NOT_EXIST_DEPARTMENT(404, "존재하지 않는 하위부서 입니다."),

    //================================employee========================================
    NOT_BLANK_NAME(400,"공백은 허용되지 않습니다."),
    NOT_EXIST_EMPLOYEE(404,"해당 직원이 존재하지 않습니다."),
    EMPLOYEE_UPDATE_WRONG_ACCESS(400,"잘못된 접근입니다."),

    //================================news========================================
    NEWS_NOT_FOUND(404,"해당 뉴스를 찾을 수 없습니다."),
    NOT_EXIST_NEWS(404,"해당 뉴스가 존재하지 않습니다."),
    NEWS_UPDATE_WRONG_ACCESS(400,"잘못된 접근입니다."),

    //================================token========================================
    INVALID_TOKEN(404,"Token이 유효하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(401, "토근이 만료되었습니다. 다시 로그인 해주세요."),

    //================================email========================================
    EMAIL_NULL_INPUT_ERROR( 501, "이메일을 입력해주세요."),
    EMAIL_INPUT_ERROR(502,  "올바른 이메일이 아닙니다."),
    INVALID_EMAIL_ERROR(503,  "이메일을 다시 확인해주세요."),
    AUTH_CODE_NOT_ISSUE(504, "먼저 인증번호를 받아주세요."),
    AUTH_CODE_NOT_CORRECT(505, "인증번호가 틀렸습니다."),

    //==============================500 INTERNAL SERVER ERROR========================
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 고객센터에 문의해주세요."),
    BIND_FAILS(500,"서버 에러입니다. 고객센터에 문의해주세요."),
    NOT_VALUE_AT(500,"서버 에러입니다. 고객센터에 문의해주세요."),
    NO_ELEMENT(500,"서버 에러입니다. 고객센터에 문의해주세요.");



    private final int status;
    private final String message;
}
