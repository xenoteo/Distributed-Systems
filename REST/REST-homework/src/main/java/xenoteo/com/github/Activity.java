package xenoteo.com.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

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
