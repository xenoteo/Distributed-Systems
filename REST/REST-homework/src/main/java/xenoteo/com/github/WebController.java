package xenoteo.com.github;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
public class WebController {
    @RequestMapping(value = "/")
    public String defaultPage(Model model, Principal principal) {
        return "index";
    }

    @RequestMapping(value = "/index")
    public String homePage(Model model, Principal principal) {
        return "index";
    }
}
