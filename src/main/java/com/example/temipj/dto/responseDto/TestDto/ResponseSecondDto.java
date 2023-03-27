package com.example.temipj.dto.responseDto.TestDto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ResponseSecondDto {
    String department;
    ResponseThirdDto contact;
}
//     {
//     "department": "AI\uc735\ud569\uae30\uc220\uc5f0\uad6c\uc18c",
//     "contact": {
//     "name": "홍길동",
//     "mobile_number": "010",
//     "email": "yusung@everybot.net"
//     }