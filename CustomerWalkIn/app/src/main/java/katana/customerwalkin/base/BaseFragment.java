package katana.customerwalkin.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import katana.customerwalkin.R;
import katana.customerwalkin.utils.Logger;
import katana.customerwalkin.utils.SoftKeyboardLayout;

public abstract class BaseFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final Logger LOGGER = Logger.getLogger(BaseFragment.class);

    protected View mRootView;

    public abstract int getLayoutId();

    @Override
    public void onGlobalLayout() {
        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    public View findViewById(int resId) {
        return mRootView.findViewById(resId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null)
                parent.removeView(mRootView);
        }
        try {
            mRootView = inflater.inflate(layoutId, container, false);
            mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            ButterKnife.bind(this, mRootView);
        } catch (InflateException e) {
            e.printStackTrace();
        }

        return mRootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setTitleActionBar(String title) {
        TextView toolBarTitle = (TextView) getActivity().findViewById(R.id.toolbarTitle);
        toolBarTitle.setText(title);
        toolBarTitle.setVisibility(View.VISIBLE);
    }

    public void updateBackActionbar(String title) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFragmentBackStack();
                    hideSoftKeyboard();
                }
            });
        }
        setTitleActionBar(title);
    }

    public void updateBackToHide(final OnBackOriginScreenListener onBackOriginScreenListener, String title) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard();
                    if (onBackOriginScreenListener != null) {
                        onBackOriginScreenListener.onBackOriginScreen(false);
                    }
                }
            });
        }
        setTitleActionBar(title);
    }

    public void handleBackToHide(OnBackOriginScreenListener onBackOriginScreenListener, boolean requestUpdate) {
        if (onBackOriginScreenListener != null) {
            onBackOriginScreenListener.onBackOriginScreen(requestUpdate);
        }
    }

    public void removeFragmentBackStack() {
        if (getFragmentManager() != null) {
            boolean canBack = getFragmentManager().getBackStackEntryCount() > 0;
            if (canBack) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        }
    }

    public void handleBackPress() {
        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
        }
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    boolean canBack = getFragmentManager().getBackStackEntryCount() > 0;
                    if (canBack) {
                        FragmentManager manager = getFragmentManager();
                        manager.popBackStack();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void handleBackPressToHide(final OnBackOriginScreenListener onBackOriginScreenListener) {
        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
        }
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    if (onBackOriginScreenListener != null) {
                        onBackOriginScreenListener.onBackOriginScreen(false);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void handleBackPressToHide(final OnBackOriginScreenListener onBackOriginScreenListener, final boolean
            requestUpdate) {
        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
        }
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    if (onBackOriginScreenListener != null) {
                        onBackOriginScreenListener.onBackOriginScreen(requestUpdate);
                    }
                    return true;
                }
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard();
                    if (onBackOriginScreenListener != null) {
                        onBackOriginScreenListener.onBackOriginScreen(true);
                    }
                }
            });
        }
    }

    public void updateToolbar(String title) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }
        setTitleActionBar(title);
    }

    private void hideSoftKeyboard() {
        try {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException ignored) {
            LOGGER.error(ignored.getMessage());
        }
    }

    public void catchEventKeyboardToHide(SoftKeyboardLayout softKeyboardLayout, final OnBackOriginScreenListener onBackOriginScreenListener) {
        softKeyboardLayout.setOnSoftKeyboardVisibilityChangeListener(
                new SoftKeyboardLayout.SoftKeyboardVisibilityChangeListener() {
                    @Override
                    public void onSoftKeyboardShow() {
                    }

                    @Override
                    public void onSoftKeyboardHide() {
                        handleBackPressToHide(onBackOriginScreenListener);
                    }
                });
    }
}
