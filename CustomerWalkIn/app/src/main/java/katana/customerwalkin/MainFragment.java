package katana.customerwalkin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import katana.customerwalkin.api.model.ColorModel;
import katana.customerwalkin.api.model.DataAuth;
import katana.customerwalkin.base.BaseFragment;
import katana.customerwalkin.feature.SelectEmployeesFragment;
import katana.customerwalkin.feature.SelectGeneralServicesFragment;
import katana.customerwalkin.feature.SettingDialog;
import katana.customerwalkin.feature.StepOneFragment;
import katana.customerwalkin.feature.StepTwoFragment;
import katana.customerwalkin.utils.ImageLoaderUtils;
import katana.customerwalkin.utils.SharePref;

public class MainFragment extends BaseFragment {

    @BindView(R.id.menu_drawerlayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    private SettingDialog settingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    private void updateUI() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        DataAuth dataAuth = SharePref.getDataAuth();
        if (dataAuth != null) {
            setTitleActionBar(dataAuth.company);
            ImageLoaderUtils.showCenterCrop(getContext(), dataAuth.logo, imgIcon);
        }
        final Fragment fragment = new StepOneFragment();
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.add(R.id.container_body, fragment).commit();
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingDialog = new SettingDialog();
                settingDialog.show(getFragmentManager(), "");
                settingDialog.setSenDataFromSettingToFragment(new SettingDialog.SenDataFromSettingToFragment() {
                    @Override
                    public void sendData(int colorText, int buttonText) {
                        toolbar.setBackgroundColor(buttonText);
                        SharePref.setColor(new ColorModel(colorText, buttonText));
                        Fragment fr = getFragmentManager().findFragmentById(R.id.container_body);
                        if (fr instanceof StepOneFragment) {
                            ((StepOneFragment) fr).updateColor(colorText, buttonText);
                        }
                        if (fr instanceof SelectEmployeesFragment) {
                            ((SelectEmployeesFragment) fr).updateColor(colorText, buttonText);
                        }
                        if (fr instanceof SelectGeneralServicesFragment) {
                            ((SelectGeneralServicesFragment) fr).updateColor(colorText, buttonText);
                        }
                        if (fr instanceof StepTwoFragment) {
                            ((StepTwoFragment) fr).updateColor(colorText, buttonText);
                        }
                    }
                });
            }
        });
    }
}