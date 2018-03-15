package katana.customerwalkin.api.service;

import java.util.Map;

import katana.customerwalkin.api.model.ResponseAuth;
import katana.customerwalkin.api.model.ResponseGetData;
import katana.customerwalkin.api.model.ResponseSave;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * ka
 * 26/09/2017
 */

public interface WalkInService {

    @FormUrlEncoded
    @POST("auth")
    Call<ResponseAuth> getAuthWalkIn(@Field("") String param);

    @FormUrlEncoded
    @POST("check")
    Call<ResponseBody> getCheck(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("getdata")
    Call<ResponseGetData> getData(@Field("store_id") String storeId);

    @FormUrlEncoded
    @POST("save")
    Call<ResponseSave> saveData(@FieldMap Map<String, String> params);
}
