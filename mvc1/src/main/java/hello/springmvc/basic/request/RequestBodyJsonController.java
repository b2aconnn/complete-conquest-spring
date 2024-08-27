package hello.springmvc.basic.request;

import hello.servlet.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RequestBodyJsonController {

    @PostMapping("/request-body-json-v3")
//    public String requestBodyJsonV3(HelloData helloData) { : @RequestBody 를 생략할 경우, @ModelAttribute 로 선언한 것과 동일하게 동작한다.
    // 어노테이션을 선언 하지 않고 타입으로만 파라미터로 선언할 경우 spring에서 내부적으로 @ModelAttribute 선언과 동일하게 동작한다.
    public HelloData requestBodyJsonV3(@RequestBody HelloData helloData) {
        // @RequestBody 는 반드시 content-type = application/json 으로 와야 변환을 해준다 !
        System.out.println("helloData = " + helloData);

        // 반환도 가능하다.
        return helloData;
    }
}
