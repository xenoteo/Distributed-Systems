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
     * https://api.openbrewerydb.org/breweries?by_state=ohio&sort=type,+name
     * https://api.openbrewerydb.org/breweries?by_city=san_diego&sort=-name
     *
     * https://api.openbrewerydb.org/breweries/search?query=dog
     *
     * wyciągnięcie średniej, znalezienie ekstremów, porównanie wartości z różnych serwisów itp.
     *
     * https://www.boredapi.com/
     * https://www.boredapi.com/api/activity?type=recreational
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
                             @RequestParam(value = "activity") String activityType,
                             Model model,
                             Principal principal) {

        Brewery[] breweries = requestBreweries(city, type, state, name);
        Activity activity = requestActivity(activityType);

        model.addAttribute("breweries", breweries);
        model.addAttribute("activity", activity);
        model.addAttribute("word", "beer".toUpperCase());

        return "result";
    }

    private String createRequestUri(String city, String type, String state, String name){
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

        return uriBuilder.toString();
    }

    private Brewery[] requestBreweries(String city, String type, String state, String name){
        String uri = createRequestUri(city, type, state, name);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Brewery[].class);
    }

    private Activity requestActivity(String activityType){
        RestTemplate restTemplate = new RestTemplate();

        String activityUri = String.format("https://www.boredapi.com/api/activity?type=%s", activityType);
        Activity activity = restTemplate.getForObject(activityUri, Activity.class);

        assert activity != null;
        if (activity.getLink() == null || activity.getLink().isEmpty()){
            activity.setLink("-");
        }

        return activity;
    }

}
