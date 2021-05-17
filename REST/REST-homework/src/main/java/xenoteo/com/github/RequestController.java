package xenoteo.com.github;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class RequestController {

    @RequestMapping(value = "/create_user")
    public String createUser(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "index") String index,
            @RequestParam(value = "role", defaultValue = "0") String role,
            Model model,
            Principal principal) {
        return "redirect:/index";
    }

}
