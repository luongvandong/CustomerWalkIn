package katana.customerwalkin.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * ka
 * 26/09/2017
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingData implements Serializable {

    @JsonProperty("services_id")
    public String serviceId;
    @JsonProperty("service_name")
    public String serviceName;
    @JsonProperty("service_price")
    public String servicePrice;
    @JsonProperty("service_bg")
    public String serviceBg;
    @JsonProperty("service_font")
    public String serviceFont;

    public boolean checked = false;

    public BookingData() {
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceBg() {
        return serviceBg;
    }

    public void setServiceBg(String serviceBg) {
        this.serviceBg = serviceBg;
    }

    public String getServiceFont() {
        return serviceFont;
    }

    public void setServiceFont(String serviceFont) {
        this.serviceFont = serviceFont;
    }
}
