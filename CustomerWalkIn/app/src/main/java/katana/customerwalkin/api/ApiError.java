package katana.customerwalkin.api;

import retrofit2.Response;

/**
 * cuongnv
 * 28/08/2017
 */

public class ApiError {
    private int statusCode;
    private String message;

    public ApiError() {

    }

    public static <T> ApiError parseFromResponse(Response<T> response) {
        ApiError apiError = new ApiError();
        apiError.message = response.message();
        apiError.statusCode = response.code();

        return apiError;
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
