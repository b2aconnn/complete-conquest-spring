package hello.servlet.web.springmvc.v2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("new-form");
    }

    @RequestMapping
    public ModelAndView save() {
        // Business Logic

        ModelAndView modelAndView = new ModelAndView("members");
//         modelAndView.getModel().put("member", "member");
        modelAndView.addObject("member", "member-value");
        return modelAndView;
    }

    @RequestMapping("/save")
    public ModelAndView members(
            HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));


        // Business Logic


        ModelAndView modelAndView = new ModelAndView("save-result");
//         modelAndView.getModel().put("member", "member");
        modelAndView.addObject("member", "member-value");
        return modelAndView;
    }
}
