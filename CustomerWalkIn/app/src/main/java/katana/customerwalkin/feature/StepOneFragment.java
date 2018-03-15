package katana.customerwalkin.feature;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import katana.customerwalkin.BarcodeActivity;
import katana.customerwalkin.R;
import katana.customerwalkin.api.ApiError;
import katana.customerwalkin.api.ServiceGenerator;
import katana.customerwalkin.api.model.ColorModel;
import katana.customerwalkin.api.model.DataAuth;
import katana.customerwalkin.api.model.UserModel;
import katana.customerwalkin.api.service.WalkInService;
import katana.customerwalkin.base.BaseChildFragment;
import katana.customerwalkin.base.OnBackOriginScreenListener;
import katana.customerwalkin.utils.ImageLoaderUtils;
import katana.customerwalkin.utils.JsonUtil;
import katana.customerwalkin.utils.Logger;
import katana.customerwalkin.utils.SharePref;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static katana.customerwalkin.utils.Constant.MEMBER_SHIP;
import static katana.customerwalkin.utils.Constant.PHONE_NUM;
import static katana.customerwalkin.utils.Constant.USER_MODEL;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepOneFragment extends BaseChildFragment implements View.OnClickListener {

    public static final int RC_QRCODE = 1011;
    private static final Logger LOGGER = Logger.getLogger(StepOneFragment.class);

    @BindView(R.id.btnBarcode)
    Button btnBarcode;
    @BindView(R.id.edtQRCode)
    EditText edtQRCode;
    @BindView(R.id.edtMobile)
    EditText edtMobile;
    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.scanBarcode)
    LinearLayout scanBarcode;

    private UserModel userModel;
    private DataAuth dataAuth;

    @Override

    public int getLayoutId() {
        return R.layout.fragment_step_one;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnTouchListener(true);
        btnSearch.setOnClickListener(this);
        btnBarcode.setOnClickListener(this);

        dataAuth = SharePref.getDataAuth();
        if (dataAuth != null) {
            String cover = dataAuth.cover;
            ImageLoaderUtils.showCenterCrop(getContext(), cover, ivCover);
        }

//        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
//                new WeakReference<>(edtMobile));
//        edtMobile.addTextChangedListener(addLineNumberFormatter);
        edtMobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        ColorModel c = SharePref.getColorModel();
        if (c != null) {
            updateColor(c.getTextColor(), c.getButtonColor());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBarcode: {
                handleActionQRCode();
                break;
            }
            case R.id.btnSearch: {
                if (!checkValidPhone()) {
                    Toast.makeText(getContext(), "Phone must be 10 or 11 number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (validateFiled()) {
                    handleActionSearch();
                }
                break;
            }
            default:
                break;
        }
    }

    private void handleActionSearch() {
        final String mobile = edtMobile.getText().toString().trim();
        WalkInService walkInService = ServiceGenerator.create(WalkInService.class);
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("owner_id", dataAuth.ownerId);
        params.put("member_ship", edtQRCode.getText().toString());
        walkInService.getCheck(params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    LOGGER.warn(ApiError.parseFromResponse(response));
                    return;
                }
                ResponseBody body = response.body();
                if (body != null) {
                    try {
                        String tmp = body.string();
                        JSONObject root = JsonUtil.createJSONObject(tmp);
                        String message = JsonUtil.getString(root, "message", "failed");
                        if (message.equals("Ok")) {
                            JSONArray data = JsonUtil.getJSONArray(root, "data");
                            if (data != null) {
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject infoRoot = JsonUtil.getJSONObject(data, i);
                                    JSONObject info = JsonUtil.getJSONObject(infoRoot, "info");
                                    if (info != null) {
                                        userModel = new UserModel();
                                        String phone = JsonUtil.getString(info, "phone", "");
                                        if (mobile.equals(phone)) {
                                            userModel.setPhoneNumber(phone);
                                            String name = JsonUtil.getString(info, "name", "");
                                            firstLastName(name.trim());
                                            String email = JsonUtil.getString(info, "email", "");
                                            userModel.setEmail(email);
                                            String customerId = JsonUtil.getString(info, "customer_id", "");
                                            userModel.setCustomerId(customerId);
                                        }
                                    }
                                }
                            }
                        }
                        handleActionNext(userModel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LOGGER.error(t);
            }
        });
    }

    private void handleActionQRCode() {
        Intent i = new Intent(getContext(), BarcodeActivity.class);
        getActivity().startActivityForResult(i, RC_QRCODE);
    }

    private boolean validateFiled() {
        return !edtMobile.getText().toString().isEmpty() || !edtQRCode.getText().toString().isEmpty();
    }

    private boolean checkValidPhone() {
        String phoneNum = edtMobile.getText().toString().trim();
        return !phoneNum.isEmpty() && phoneNum.length() == 10 || phoneNum.length() == 11;
    }

    private void handleActionNext(final UserModel userModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_MODEL, userModel);
        bundle.putString(PHONE_NUM, edtMobile.getText().toString().trim());
        final StepTwoFragment stepTwoFragment = StepTwoFragment.newInstance(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.container_body, stepTwoFragment).commit();

        stepTwoFragment.setOnBackOriginScreenListener(new OnBackOriginScreenListener() {
            @Override
            public void onBackOriginScreen(boolean requestUpdate) {
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.remove(stepTwoFragment).commit();
                StepOneFragment.this.userModel = null;
                ColorModel c = SharePref.getColorModel();
                if (c != null) {
                    updateColor(c.getTextColor(), c.getButtonColor());
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String qrCode = data.getStringExtra(MEMBER_SHIP);
            edtQRCode.setText(qrCode);
        }
    }

    public void updateColor(int textColor, int buttonColor) {
        btnBarcode.setBackgroundColor(buttonColor);
        btnSearch.setBackgroundColor(buttonColor);
        btnBarcode.setTextColor(textColor);
        btnSearch.setTextColor(textColor);
    }

    private void firstLastName(String name) {
        char[] tmp = name.toCharArray();
        String lastName = "";
        String firstName = "";
        for (int i = 0; i < tmp.length - 1; i++) {
            if (tmp[i + 1] == ' ') {
                lastName = name.substring(0, i + 1);
                firstName = name.substring(i + 2, tmp.length);
            }
        }
        userModel.setLastName(lastName);
        userModel.setFirstName(firstName);
    }
}
