package katana.customerwalkin.api.model;

import java.io.Serializable;

/**
 * ka
 * 26/09/2017
 */

public class ResponseAuth implements Serializable {

    public String status;
    public String message;
    public DataAuth data;

    public ResponseAuth() {
    }
}
