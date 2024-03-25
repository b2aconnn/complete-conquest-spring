package com.example.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// reason : messageSource에서 먼저 찾은 다음, 존재하지 않으면 문자열 그대로 출력
// 동적으로 값들을 설정하기 어렵다는 단점이 있다.
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException {
}
