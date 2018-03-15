package katana.customerwalkin.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * ka
 * 26/09/2017
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSave implements Serializable {

    public String id;
    @JsonProperty("Store_ID")
    public String storeId;
    @JsonProperty("employee_id")
    public String employeeId;
    public String title;
    @JsonProperty("customer_name")
    public String customerName;
    @JsonProperty("customer_id")
    public String customerId;
    public String telephone;
    public String address;
    public String city;
    public String state;
    public String zipcode;
    public String content;
    public String notes;
    @JsonProperty("start_dated")
    public String startDate;
    @JsonProperty("end_dated")
    public String endDate;
    public int deleted;
    public int type;
    @JsonProperty("action_soft")
    public String actionSoft;
    @JsonProperty("date_created_soft")
    public String dateCreatedSoft;
}
