package katana.customerwalkin.feature;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import katana.customerwalkin.MainFragment;
import katana.customerwalkin.R;
import katana.customerwalkin.api.ApiError;
import katana.customerwalkin.api.ServiceGenerator;
import katana.customerwalkin.api.model.BookingData;
import katana.customerwalkin.api.model.ColorModel;
import katana.customerwalkin.api.model.DataSave;
import katana.customerwalkin.api.model.Employee;
import katana.customerwalkin.api.model.ResponseSave;
import katana.customerwalkin.api.model.UserModel;
import katana.customerwalkin.api.service.WalkInService;
import katana.customerwalkin.base.BaseChildFragment;
import katana.customerwalkin.base.OnBackOriginScreenListenerWithData;
import katana.customerwalkin.feature.adapter.ConfirmDialog;
import katana.customerwalkin.feature.adapter.ServiceAdapter;
import katana.customerwalkin.utils.Logger;
import katana.customerwalkin.utils.SharePref;
import katana.customerwalkin.utils.widget.ExRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static katana.customerwalkin.utils.Constant.BOOK_DATA;
import static katana.customerwalkin.utils.Constant.CUSTOMER_ID;
import static katana.customerwalkin.utils.Constant.EMPLOYEE;
import static katana.customerwalkin.utils.Constant.USER_MODEL;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectGeneralServicesFragment extends BaseChildFragment implements View.OnClickListener {

    private static final Logger LOGGER = Logger.getLogger(SelectGeneralServicesFragment.class);
    @BindView(R.id.btnRegisterMoreThan)
    Button btnMoreThan;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.rcvService)
    RecyclerView rcvService;

    private List<BookingData> bookingDataList;
    private ServiceAdapter serviceAdapter;
    private OnBackOriginScreenListenerWithData onBackOriginScreenListener;
    private Employee employee;
    private String customerId;
    private UserModel userModel;
    private DataSave dataSave;

    public static SelectGeneralServicesFragment newInstance(Bundle b) {
        SelectGeneralServicesFragment selectGeneralServicesFragment = new SelectGeneralServicesFragment();
        selectGeneralServicesFragment.setArguments(b);
        return selectGeneralServicesFragment;
    }

    public void setOnBackOriginScreenListener(OnBackOriginScreenListenerWithData onBackOriginScreenListener) {
        this.onBackOriginScreenListener = onBackOriginScreenListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_general_sevices;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnTouchListener(true);
        btnDone.setOnClickListener(this);
        btnMoreThan.setOnClickListener(this);
        updateUI();

        ColorModel c = SharePref.getColorModel();
        if (c != null) {
            updateColor(c.getTextColor(), c.getButtonColor());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone: {
                callbackRegisterScreen();
                break;
            }
            case R.id.btnRegisterMoreThan: {
                handleActionDone();
                break;
            }
            default:
                break;
        }
    }

    private void callbackRegisterScreen() {
        boolean isCheck = false;
        BookingData book = new BookingData();
        if (bookingDataList != null) {
            for (BookingData b : bookingDataList) {
                isCheck = b.checked;
                book = b;
            }
            if (isCheck && employee != null) {
                WalkInService walkInService = ServiceGenerator.create(WalkInService.class);
                Map<String, String> params = new HashMap<>();
                params.put("owner_id", SharePref.getDataAuth().ownerId);
                params.put("store_id", SharePref.getDataAuth().storeId);
                params.put("employee_id", employee.employeeId);
                params.put("service_id", book.getServiceId());
                params.put("customer_id", customerId);
                params.put("mobile", userModel.getPhoneNumber());
                params.put("fullname", userModel.getLastName() + " " + userModel.getFirstName());

                for (Map.Entry entry : params.entrySet()) {
                    LOGGER.info(entry.getKey() + " : " + entry.getValue());
                }
                walkInService.saveData(params).enqueue(new Callback<ResponseSave>() {
                    @Override
                    public void onResponse(Call<ResponseSave> call, Response<ResponseSave> response) {
                        if (!response.isSuccessful()) {
                            LOGGER.warn(ApiError.parseFromResponse(response));
                            return;
                        }
                        ResponseSave responseSave = response.body();
                        if (responseSave != null) {
                            dataSave = responseSave.data;
                            Bundle bundle = getArguments();
                            bundle.putString(CUSTOMER_ID, dataSave.customerId);
                            if (userModel.getCustomerId() == null || userModel.getCustomerId().isEmpty()) {
                                userModel.setCustomerId(dataSave.customerId);
                            }
                            showConfirmDialog(userModel);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSave> call, Throwable t) {
                        LOGGER.error(t);
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please select service", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleActionDone() {
        boolean isCheck = false;
        BookingData book = new BookingData();
        if (bookingDataList != null) {
            for (BookingData b : bookingDataList) {
                isCheck = b.checked;
                book = b;
            }
            if (isCheck && employee != null) {
                WalkInService walkInService = ServiceGenerator.create(WalkInService.class);
                Map<String, String> params = new HashMap<>();
                params.put("owner_id", SharePref.getDataAuth().ownerId);
                params.put("store_id", SharePref.getDataAuth().storeId);
                params.put("employee_id", employee.employeeId);
                params.put("service_id", book.getServiceId());
                params.put("customer_id", customerId);
                params.put("mobile", userModel.getPhoneNumber());
                params.put("fullname", userModel.getLastName() + " " + userModel.getFirstName());

                for (Map.Entry entry : params.entrySet()) {
                    LOGGER.info(entry.getKey() + " : " + entry.getValue());
                }
                walkInService.saveData(params).enqueue(new Callback<ResponseSave>() {
                    @Override
                    public void onResponse(Call<ResponseSave> call, Response<ResponseSave> response) {
                        if (!response.isSuccessful()) {
                            LOGGER.warn(ApiError.parseFromResponse(response));
                            return;
                        }
                        ResponseSave responseSave = response.body();
                        if (responseSave != null) {
                            dataSave = responseSave.data;
                            Bundle bundle = getArguments();
                            bundle.putString(CUSTOMER_ID, dataSave.customerId);
                            onBackOriginScreenListener.onBackOriginScreenWithData(bundle);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSave> call, Throwable t) {
                        LOGGER.error(t);
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please select service", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateUI() {
        userModel = new UserModel();
        dataSave = new DataSave();
        bookingDataList = new ArrayList<>();
        Type type = new TypeToken<List<BookingData>>() {
        }.getType();
        Gson g = new Gson();
        String book = getArguments().getString(BOOK_DATA);
        bookingDataList = g.fromJson(book, type);
        rcvService.setHasFixedSize(true);
        rcvService.setLayoutManager(new GridLayoutManager(getContext(), 5));

        serviceAdapter = new ServiceAdapter(getContext(), bookingDataList);
        rcvService.setAdapter(serviceAdapter);

        serviceAdapter.setOnItemClickListener(new ExRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                bookingDataList.get(position).checked = true;
            }
        });
        employee = (Employee) getArguments().getSerializable(EMPLOYEE);
        customerId = getArguments().getString(CUSTOMER_ID);
        userModel = (UserModel) getArguments().getSerializable(USER_MODEL);
    }

    public void updateColor(int textColor, int buttonColor) {
        btnDone.setBackgroundColor(buttonColor);
        btnMoreThan.setBackgroundColor(buttonColor);
        btnDone.setTextColor(textColor);
        btnMoreThan.setTextColor(textColor);
    }

    private void showConfirmDialog(final UserModel userModel) {
        ConfirmDialog confirmDialog = new ConfirmDialog("Would you like register more than?");
        confirmDialog.show(getFragmentManager(), confirmDialog.getTag());
        confirmDialog.setCancelable(false);
        confirmDialog.setOnOkListener(new ConfirmDialog.OnOkListener() {
            @Override
            public void onOkListener() {
//                handleActionDone();
                btnMoreThan.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.GONE);
            }

            @Override
            public void onCancelListener() {
                List<Fragment> fragments = getFragmentManager().getFragments();
                for (int i = 0; i < fragments.size(); i++) {
                    Fragment fragment = fragments.get(i);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (fragment instanceof MainFragment || fragment instanceof SupportRequestManagerFragment || fragment instanceof
                            StepOneFragment) {

                    } else {
                        transaction.remove(fragment).commitAllowingStateLoss();
                    }
                }
            }
        });
    }
}
