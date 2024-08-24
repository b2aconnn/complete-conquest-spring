package hello.servlet.web.springmvc.v3;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username,
            @RequestParam("age") int age,
            Model model) {
        // Business Logic

        model.addAttribute("member", "member-value");
        return "member-save";
    }

    @GetMapping
    public String members(Model model) {

        // Business Logic

        model.addAttribute("member", "member-value");
        return "members";
    }
}
