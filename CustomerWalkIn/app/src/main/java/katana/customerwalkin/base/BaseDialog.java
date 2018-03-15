package katana.customerwalkin.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}