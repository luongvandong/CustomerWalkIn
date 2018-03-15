package katana.customerwalkin.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ka
 * 26/09/2017
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataAuth {

    public String logo;
    public String cover;
    public String company;

    @JsonProperty("owner_id")
    public String ownerId;
    @JsonProperty("store_id")
    public String storeId;

    public DataAuth() {
    }
}
