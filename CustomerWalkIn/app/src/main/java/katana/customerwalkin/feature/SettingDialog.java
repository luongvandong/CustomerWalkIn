package katana.customerwalkin.feature;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import katana.customerwalkin.R;
import katana.customerwalkin.api.model.ColorModel;
import katana.customerwalkin.base.BaseDialogFragment;
import katana.customerwalkin.utils.SharePref;

/**
 * Created by admin on 9/26/2017.
 */

public class SettingDialog extends BaseDialogFragment implements View.OnClickListener {

    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.btn_dialog_end_game_close)
    Button btnDialogEndGameClose;
    @BindView(R.id.bt_textColor)
    Button buttonTextColor;
    @BindView(R.id.bt_color)
    Button buttonColor;

    int arrayColorButton;
    int arrayColorTextButton;

    SharedPreferences sharedPreferences;
    SenDataFromSettingToFragment senDataFromSettingToFragment;

    List<String> listButtonColor;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_setting;
    }

    public void setSenDataFromSettingToFragment(SenDataFromSettingToFragment senDataFromSettingToFragment) {
        this.senDataFromSettingToFragment = senDataFromSettingToFragment;
    }

    @Override
    protected void updateUI(View view) {
        imgClose.setOnClickListener(this);
        buttonColor.setOnClickListener(this);
        buttonTextColor.setOnClickListener(this);
        btnDialogEndGameClose.setOnClickListener(this);
        listButtonColor = Arrays.asList(getResources().getStringArray(R.array.color_choices));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                dismiss();
                break;
            case R.id.btn_dialog_end_game_close:
                saveColor();
                senDataFromSettingToFragment.sendData(arrayColorTextButton, arrayColorButton);
                getDialog().dismiss();
                break;
            case R.id.bt_color:
                ColorChooserDialog dialog = new ColorChooserDialog(getActivity());
                dialog.setTitle("Choose Color");
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        arrayColorButton = color;
                        buttonColor.setBackgroundColor(color);
                        buttonTextColor.setBackgroundColor(color);
                    }
                });
                //customize the dialog however you want
                dialog.show();
                break;
            case R.id.bt_textColor:
                ColorChooserDialog dialog1 = new ColorChooserDialog(getActivity());
                dialog1.setTitle("Choose Color");
                dialog1.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        arrayColorTextButton = color;
                        buttonColor.setTextColor(color);
                        buttonTextColor.setTextColor(color);
                    }
                });
                //customize the dialog however you want
                dialog1.show();
                break;
        }
    }

    private void saveColor() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        arrayColorButton = ((ColorDrawable) buttonColor.getBackground()).getColor();
        arrayColorTextButton = buttonTextColor.getCurrentTextColor();
        editor.putInt("BUTTON_COLOR", arrayColorButton);
        editor.putInt("BUTTON_TEXT_COLOR", arrayColorTextButton);
        editor.commit();

    }

    public interface SenDataFromSettingToFragment {
        void sendData(int colorText, int buttonText);
    }

    @Override
    public void onPause() {
        super.onPause();
      /*  ColorModel colorModel = SharePref.getColorModel();
        buttonColor.setBackgroundColor(colorModel.getButtonColor());
        buttonTextColor.setBackgroundColor(colorModel.getButtonColor());
        buttonTextColor.setTextColor(colorModel.getTextColor());
        buttonTextColor.setTextColor(colorModel.getTextColor());*/
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        int buttonCorlor1 = sharedPreferences.getInt("BUTTON_COLOR", ContextCompat.getColor(getActivity(), R.color.blue1));
        int buttonTextColor1 = sharedPreferences.getInt("BUTTON_TEXT_COLOR", Color.WHITE);
        buttonColor.setBackgroundColor(buttonCorlor1);
        buttonTextColor.setBackgroundColor(buttonCorlor1);
        buttonColor.setTextColor(buttonTextColor1);
        buttonTextColor.setTextColor(buttonTextColor1);
    }
}
