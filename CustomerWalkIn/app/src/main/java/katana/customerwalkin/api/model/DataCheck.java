package katana.customerwalkin.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * ka
 * 26/09/2017
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCheck implements Serializable {
    public String code;
}
