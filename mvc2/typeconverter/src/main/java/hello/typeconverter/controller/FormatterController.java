package hello.typeconverter.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class FormatterController {
    @GetMapping("/formatter/edit")
    public Object formatterForm(Model model) {
        Form form = new Form();
        form.setNumber(100000);
//        form.setLocalDateTime(LocalDateTime.now());
        model.addAttribute("form", form);

        return form;
    }

    @PostMapping("/formatter/edit/{number}")
    public Object formatterEdit(@PathVariable("number") Integer number, @RequestBody Form form) {
        return form;
    }

    @Data
    static class Form {
        // rest api 에서는 Formmater를 요청, 응답을 처리하지 못 한다. HttpMessageConverter를 보면
        // 이 경우 jackson 라이브러리를 사용해서 convert 하기 떄문에 Formatter가 적용되지 않는다.
        // @PathVariable, @ModelAttribute, @RequestParam 에서는 Formatter를 적용할 수 있음
//        @NumberFormat(pattern = "###,###")
        private Integer number;

//        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private String localDateTime;
    }
}
