package katana.customerwalkin.feature;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import katana.customerwalkin.R;
import katana.customerwalkin.WalkInApplication;
import katana.customerwalkin.api.model.ColorModel;
import katana.customerwalkin.api.model.DataAuth;
import katana.customerwalkin.api.model.UserModel;
import katana.customerwalkin.base.BaseChildFragment;
import katana.customerwalkin.base.OnBackOriginScreenListener;
import katana.customerwalkin.utils.ImageLoaderUtils;
import katana.customerwalkin.utils.Logger;
import katana.customerwalkin.utils.SharePref;

import static katana.customerwalkin.utils.Constant.PHONE_NUM;
import static katana.customerwalkin.utils.Constant.USER_MODEL;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepTwoFragment extends BaseChildFragment implements View.OnClickListener {

    private static final Logger LOGGER = Logger.getLogger(StepTwoFragment.class);
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;
    @BindView(R.id.edtMobile)
    EditText edtMobile;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnCreateNew)
    Button btnCreateNew;
    @BindView(R.id.ivCover)
    ImageView ivCover;

    private UserModel userModel;
    private String phoneNum;
    private OnBackOriginScreenListener onBackOriginScreenListener;

    public static StepTwoFragment newInstance(Bundle b) {
        StepTwoFragment stepTwoFragment = new StepTwoFragment();
        stepTwoFragment.setArguments(b);
        return stepTwoFragment;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setOnBackOriginScreenListener(OnBackOriginScreenListener onBackOriginScreenListener) {
        this.onBackOriginScreenListener = onBackOriginScreenListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_step_zero;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnTouchListener(true);
        btnBack.setOnClickListener(this);
        btnCreateNew.setOnClickListener(this);
        userModel = (UserModel) getArguments().getSerializable(USER_MODEL);
        if (userModel == null) {
            btnCreateNew.setText(WalkInApplication.getInstance().getString(R.string.create_new));
            userModel = new UserModel();
        }
        phoneNum = getArguments().getString(PHONE_NUM);
        updateUI();
        validate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack: {
                onBackOriginScreenListener.onBackOriginScreen(true);
                break;
            }
            case R.id.btnCreateNew: {
                if (!checkValidPhone()) {
                    return;
                }
                if (validateEditText()) {
                    handleNext();
                }
                break;
            }
            default:
                break;
        }
    }

    private void handleNext() {
        Bundle bundle = new Bundle();
        String lastName = edtLastName.getText().toString().trim();
        String firstName = edtFirstName.getText().toString().trim();
        String phone = edtMobile.getText().toString().trim();
        userModel.setLastName(!lastName.isEmpty() ? lastName : "last");
        userModel.setFirstName(!firstName.isEmpty() ? firstName : "first");
        userModel.setPhoneNumber(phone);
        bundle.putSerializable(USER_MODEL, userModel);
        final SelectEmployeesFragment selectEmployeesFragment = SelectEmployeesFragment.newInstance(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.container_body, selectEmployeesFragment).commit();

        selectEmployeesFragment.setOnBackOriginScreenListener(new OnBackOriginScreenListener() {
            @Override
            public void onBackOriginScreen(boolean requestUpdate) {
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.remove(selectEmployeesFragment).commit();
                ColorModel c = SharePref.getColorModel();
                if (c != null) {
                    updateColor(c.getTextColor(), c.getButtonColor());
                }
            }
        });
    }

    public void updateColor(int textColor, int buttonColor) {
        btnCreateNew.setBackgroundColor(buttonColor);
        btnBack.setBackgroundColor(buttonColor);
        btnCreateNew.setTextColor(textColor);
        btnBack.setTextColor(textColor);
    }

    private void updateUI() {
        ColorModel c = SharePref.getColorModel();
        if (c != null) {
            updateColor(c.getTextColor(), c.getButtonColor());
        }

        DataAuth dataAuth = SharePref.getDataAuth();
        if (dataAuth != null) {
            String cover = dataAuth.cover;
            ImageLoaderUtils.showCenterCrop(getContext(), cover, ivCover);
        }

        if (!phoneNum.isEmpty()) {
            edtMobile.setText(phoneNum);
        }

//        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
//                new WeakReference<>(edtMobile));
//        edtMobile.addTextChangedListener(addLineNumberFormatter);
        edtMobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        if (userModel != null) {
            edtFirstName.setText(userModel.getFirstName());
            edtLastName.setText(userModel.getLastName());
        }
    }

    private void validate() {
        edtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LOGGER.error(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                int iLen = s.length();
                if (iLen > 0 && !String.valueOf(s.charAt(iLen - 1)).matches("[a-zA-Z0-9]+")) {
                    s.delete(iLen - 1, iLen);
                }
            }
        });
        edtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LOGGER.error(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                int iLen = s.length();
                if (iLen > 0 && !String.valueOf(s.charAt(iLen - 1)).matches("[a-zA-Z0-9]+")) {
                    s.delete(iLen - 1, iLen);
                }
            }
        });
    }

    private boolean validateEditText() {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        if (firstName.length() < 4) {
            Toast.makeText(getContext(), "First name must be > 3 characters", Toast.LENGTH_SHORT).show();
            edtFirstName.requestFocus();
            return false;
        }
        if (lastName.length() < 4) {
            Toast.makeText(getContext(), "Last name must be > 3 characters", Toast.LENGTH_SHORT).show();
            edtLastName.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkValidPhone() {
        String phoneNum = edtMobile.getText().toString().trim();
        return !phoneNum.isEmpty() && phoneNum.length() == 10 || phoneNum.length() == 11;
    }
}
