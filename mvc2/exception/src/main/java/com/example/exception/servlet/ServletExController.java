package com.example.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {
    @GetMapping("/error-ex")
    public void errorEx() {
        // 예외 처리가 되지 않으면(try..catch) WAS 까지 전달이 되어 was는 서버에서 처리하지 못 하는 상태라고 판단하여 500 status code를 return함
        throw new RuntimeException("예외 발생!");
    }
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        /**
         * controller -> 인터셉터 -> servlet -> 필터 -> WAS 까지 예외가 발생하지 않고 return...하다가
         * WAS(서블릿 컨테이너)에서 sendError()가 호출 되었는 지 확인 후, 상태 코드에 맞춰서 응답을 진행함
         */
        response.sendError(404, "404 error !");
    }
    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400, "400 error !");
    }
    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}
