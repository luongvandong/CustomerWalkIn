package katana.customerwalkin.feature;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import katana.customerwalkin.R;
import katana.customerwalkin.api.ApiError;
import katana.customerwalkin.api.ServiceGenerator;
import katana.customerwalkin.api.model.BookingData;
import katana.customerwalkin.api.model.ColorModel;
import katana.customerwalkin.api.model.Employee;
import katana.customerwalkin.api.model.ResponseGetData;
import katana.customerwalkin.api.model.UserModel;
import katana.customerwalkin.api.service.WalkInService;
import katana.customerwalkin.base.BaseChildFragment;
import katana.customerwalkin.base.OnBackOriginScreenListener;
import katana.customerwalkin.base.OnBackOriginScreenListenerWithData;
import katana.customerwalkin.feature.adapter.EmployeeAdapter;
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
public class SelectEmployeesFragment extends BaseChildFragment implements View.OnClickListener {

    private static final Logger LOGGER = Logger.getLogger(SelectEmployeesFragment.class);

    @BindView(R.id.btnGoBack)
    Button btnGoBack;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.rcvSelectEmployee)
    ExRecyclerView rcvSelectEmployee;

    private EmployeeAdapter employeeAdapter;
    private List<Employee> employees;
    private List<BookingData> bookingData;
    private OnBackOriginScreenListener onBackOriginScreenListener;
    private String customerId;
    private UserModel userModel;

    public static SelectEmployeesFragment newInstance(Bundle b) {
        SelectEmployeesFragment s = new SelectEmployeesFragment();
        s.setArguments(b);
        return s;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setOnBackOriginScreenListener(OnBackOriginScreenListener onBackOriginScreenListener) {
        this.onBackOriginScreenListener = onBackOriginScreenListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_employees;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnTouchListener(true);
        btnGoBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        updateUI();
        userModel = (UserModel) getArguments().getSerializable(USER_MODEL);
        if (userModel != null) {
            if (userModel.getCustomerId() != null && !userModel.getCustomerId().isEmpty()) {
                customerId = userModel.getCustomerId();
            }
        }
        ColorModel c = SharePref.getColorModel();
        if (c != null) {
            updateColor(c.getTextColor(), c.getButtonColor());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoBack: {
                onBackOriginScreenListener.onBackOriginScreen(true);
                break;
            }
            case R.id.btnNext: {
                handleActionNext();
                break;
            }
            default:
                break;
        }
    }

    private void handleActionNext() {
        Employee employee = null;
        Bundle bundle = getArguments();
        for (Employee e : employees) {
            if (e.checked) {
                employee = e;
                bundle.putSerializable(EMPLOYEE, employee);
            }
        }
        if (employee != null) {
            Gson g = new Gson();
            String bookingData = g.toJson(this.bookingData);
            bundle.putString(BOOK_DATA, bookingData);
            if (customerId != null) {
                bundle.putString(CUSTOMER_ID, customerId);
            } else {
                bundle.putString(CUSTOMER_ID, "");
            }
            final SelectGeneralServicesFragment selectGeneralServicesFragment = SelectGeneralServicesFragment.newInstance(bundle);
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.add(R.id.container_body, selectGeneralServicesFragment).commit();

            selectGeneralServicesFragment.setOnBackOriginScreenListener(new OnBackOriginScreenListenerWithData() {
                @Override
                public void onBackOriginScreenWithData(Bundle b) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(selectGeneralServicesFragment).commit();
                    if (b != null) {
                        customerId = b.getString(CUSTOMER_ID);
                    }
                    ColorModel c = SharePref.getColorModel();
                    if (c != null) {
                        updateColor(c.getTextColor(), c.getButtonColor());
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "Please select employee", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        employees = new ArrayList<>();
        rcvSelectEmployee.setHasFixedSize(true);
        rcvSelectEmployee.setLayoutManager(new GridLayoutManager(getContext(), 7));

        employeeAdapter = new EmployeeAdapter(getContext(), employees);
        rcvSelectEmployee.setAdapter(employeeAdapter);

        WalkInService walkInService = ServiceGenerator.create(WalkInService.class);
        walkInService.getData(SharePref.getDataAuth().storeId).enqueue(new Callback<ResponseGetData>() {
            @Override
            public void onResponse(Call<ResponseGetData> call, Response<ResponseGetData> response) {
                if (!response.isSuccessful()) {
                    LOGGER.warn(ApiError.parseFromResponse(response));
                    return;
                }
                ResponseGetData responseGetData = response.body();
                if (responseGetData != null) {
                    employees = responseGetData.data.employees;
                    bookingData = responseGetData.data.bookingDatas;
                    employees.remove(0);
                    employeeAdapter.setEmployees(employees);
                    btnNext.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetData> call, Throwable t) {
                LOGGER.error(t);
            }
        });
        employeeAdapter.setOnItemClickListener(new ExRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                employees.get(position).checked = true;
            }
        });
    }

    public void updateColor(int textColor, int buttonColor) {
        btnNext.setBackgroundColor(buttonColor);
        btnGoBack.setBackgroundColor(buttonColor);
        btnNext.setTextColor(textColor);
        btnGoBack.setTextColor(textColor);
    }
}
