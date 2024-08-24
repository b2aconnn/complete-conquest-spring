package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class SpringMemberListControllerV1 {
    @RequestMapping("/springmvc/v1/members")
    public ModelAndView process() {
        // Business Logic

        ModelAndView modelAndView = new ModelAndView("members");
//         modelAndView.getModel().put("member", "member");
        modelAndView.addObject("member", "member-value");
        return modelAndView;
     }
}
