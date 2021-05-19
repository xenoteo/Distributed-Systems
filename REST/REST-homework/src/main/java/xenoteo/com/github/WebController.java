package xenoteo.com.github;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;


@Controller
public class WebController {
    @RequestMapping(value = "/")
    public String defaultPage() {
        return "index";
    }

    @RequestMapping(value = "/index")
    public String homePage() {
        return "index";
    }

    /**
     * https://api.openbrewerydb.org/breweries?by_city=san_diego
     *
     * https://api.openbrewerydb.org/breweries?by_name=cooper
     *
     * https://api.openbrewerydb.org/breweries?by_state=ohio
     * https://api.openbrewerydb.org/breweries?by_state=new_york
     * https://api.openbrewerydb.org/breweries?by_state=new%20mexico
     *
     * https://api.openbrewerydb.org/breweries?by_type=micro
     *
     * https://api.openbrewerydb.org/breweries?by_state=ohio&sort=type,+name
     * https://api.openbrewerydb.org/breweries?by_city=san_diego&sort=-name
     *
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public String resultPage(@RequestParam(value = "city") String city,
                             @RequestParam(value = "type") String type,
                             @RequestParam(value = "state") String state,
                             @RequestParam(value = "name") String name,
                             Model model,
                             Principal principal) {

        StringBuilder uriBuilder = new StringBuilder("https://api.openbrewerydb.org/breweries?");

        if (city != null && !city.isEmpty()){
            uriBuilder.append("by_city=");
            uriBuilder.append(city.replace(' ', '_'));
            uriBuilder.append("&");
        }

        if (type != null && !type.isEmpty()){
            uriBuilder.append("by_type=");
            uriBuilder.append(type.replace(' ', '_'));
            uriBuilder.append("&");
        }

        if (state != null && !state.isEmpty()){
            uriBuilder.append("by_state=");
            uriBuilder.append(state.replace(' ', '_'));
            uriBuilder.append("&");
        }


        if (name != null && ! name.isEmpty()){
            uriBuilder.append("by_name=");
            uriBuilder.append(name.replace(' ', '_'));
            uriBuilder.append("&");
        }

        RestTemplate restTemplate = new RestTemplate();
        Brewery[] breweries = restTemplate.getForObject(uriBuilder.toString(), Brewery[].class);

        model.addAttribute("breweries", breweries);

        return "result";
    }

}
