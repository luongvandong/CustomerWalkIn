package katana.customerwalkin.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * ka
 * 26/09/2017
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data implements Serializable {

    @JsonProperty("employee_data")
    public List<Employee> employees;

    @JsonProperty("booking_data")
    public List<BookingData> bookingDatas;
}
