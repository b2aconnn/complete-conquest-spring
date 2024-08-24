package hello.servlet.web.springmvc.v1;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SpringMemberSaveControllerV1 {
    @RequestMapping("/springmvc/v1/members/save")
    public ModelAndView process(
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
