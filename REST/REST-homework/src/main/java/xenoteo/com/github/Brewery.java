package xenoteo.com.github;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Brewery {
    int id;
    String obdb_id;
    String name;
    /**
     * Must be one of:
     * <ul>
     *     <li>
     *         micro -
     *         Most craft breweries. For example, Samual Adams is still considered a micro brewery.
     *     </li>
     *     <li>
     *         nano -
     *         An extremely small brewery which typically only distributes locally.
     *     </li>
     *     <li>
     *         regional -
     *         A regional location of an expanded brewery. Ex. Sierra Nevada's Asheville, NC location.
     *     </li>
     *     <li>
     *         brewpub -
     *         A beer-focused restaurant or restaurant/bar with a brewery on-premise.
     *     </li>
     *     <li>
     *         large -
     *         A very large brewery. Likely not for visitors. Ex. Miller-Coors. (deprecated)
     *     </li>
     *     <li>
     *         planning -
     *         A brewery in planning or not yet opened to the public.
     *     </li>
     *     <li>
     *         bar -
     *         A bar. No brewery equipment on premise. (deprecated)
     *     </li>
     *     <li>
     *         contract -
     *         A brewery that uses another brewery's equipment.
     *     </li>
     *     <li>
     *         proprietor -
     *         Similar to contract brewing but refers more to a brewery incubator.
     *     </li>
     *     <li>
     *         closed -
     *         A location which has been closed.
     *     </li>
     * </ul>
     *
     */
    String brewery_type;
    String street;
    String address_2;
    String address_3;
    String city;
    String state;
    String county_province;
    String postal_code;
    String country;
    double longitude;
    double latitude;
    String phone;
    String website_url;
    String updated_at;
    String created_at;
}
