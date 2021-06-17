package xenoteo.com.github;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * The controller handling requests.
 */
@Controller
public class WebController {

    /**
     * The home page.
     *
     * @return the template of the home page.
     */
    @RequestMapping(value = "/")
    public String defaultPage() {
        return "index";
    }

    /**
     * The home page.
     *
     * @return the template of the home page.
     */
    @RequestMapping(value = "/index")
    public String homePage() {
        return "index";
    }

    /**
     * Handles the request.
     *
     * @param city  the city
     * @param type  the type
     * @param state  the state
     * @param name  the name
     * @param activityType  the activity type
     * @param model  the model
     * @return the template of the request result page
     */
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String resultPage(@RequestParam(value = "city") String city,
                             @RequestParam(value = "type") String type,
                             @RequestParam(value = "state") String state,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "activity") String activityType,
                             Model model) {

        Brewery[] breweries = requestBreweries(city, type, state, name);
        Activity activity = requestActivity(activityType);

        model.addAttribute("breweries", breweries);
        model.addAttribute("activity", activity);
        model.addAttribute("words", new Stats().findTop3Words(breweries));

        return "result";
    }

    /**
     * Creates the request URI for the brewery request.
     *
     * @param city  the city
     * @param type  the type
     * @param state  the state
     * @param name  the name
     * @return the request URI
     */
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

    /**
     * Makes a request to Open Brewery DB (https://www.openbrewerydb.org).
     *
     * @param city  the city
     * @param type  the type
     * @param state  the state
     * @param name  the name
     * @return the array of returned breweries
     */
    private Brewery[] requestBreweries(String city, String type, String state, String name){
        String uri = createRequestUri(city, type, state, name);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Brewery[].class);
    }

    /**
     * Makes a request to the Bored API (https://www.boredapi.com).
     *
     * @param activityType  the activity type
     * @return the returned activity
     */
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
