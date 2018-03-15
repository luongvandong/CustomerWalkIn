package katana.customerwalkin.feature.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import katana.customerwalkin.R;
import katana.customerwalkin.base.BaseDialogFragment;

/**
 * ka
 * 16/09/2017
 */

public class ConfirmDialog extends BaseDialogFragment implements View.OnClickListener {

    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    private OnOkListener onOkListener;
    private String msg;
    private String title;

    public ConfirmDialog(String msg) {
        this.msg = msg;
        this.title = "Success";
    }

    public ConfirmDialog(String title, String msg) {
        this.msg = msg;
        this.title = title;
    }

    public void setOnOkListener(OnOkListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_dialog_confirm;
    }

    @Override
    protected void updateUI(View view) {
        ButterKnife.bind(this, view);
        tvMessage.setText(msg);
        tvTitle.setText(title);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                onOkListener.onOkListener();
                dismissAllowingStateLoss();
                break;
            case R.id.btnCancel: {
                onOkListener.onCancelListener();
                dismissAllowingStateLoss();
                break;
            }
            default:
                break;
        }
    }

    public interface OnOkListener {
        void onOkListener();

        void onCancelListener();
    }
}
