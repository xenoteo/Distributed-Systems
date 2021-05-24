package xenoteo.com.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * The class representing an activity to perform while drinking beer.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Activity {
    String activity;
    String type;
    int participants;
    double price;
    String link;
}
