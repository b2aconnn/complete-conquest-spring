package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    // 기본적인 RequestMapping 방법 - value만 존재할 경우 모든 http method들을 허용, 특정 http method를 지정도 가능
    @RequestMapping(value = {"/hello-basic", "/hello-go"}, method = RequestMethod.GET)
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    // pathVariable 방법
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("mappingPath userId={}", userId);

        return "ok";
    }

    // 요청 파라미터 방법 중 하나 - params 에 있는 쿼리스트링 값이 있어야 호출이 가능하다
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    // 요청 헤더 요청 시 필수 값 설정 - headers 에 있는 쿼리스트링 값이 있어야 호출이 가능하다
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    // 미디어 타입 조건 매핑
    @GetMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsume() {
        log.info("mappingConsume");
        return "ok";
    }

    // 미디어 타입 조건 매핑 - accept, produce
    @GetMapping(value = "/mapping-Produces", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
