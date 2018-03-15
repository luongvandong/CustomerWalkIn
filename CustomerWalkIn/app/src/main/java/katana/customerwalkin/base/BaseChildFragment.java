package katana.customerwalkin.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import katana.customerwalkin.R;
import katana.customerwalkin.utils.Logger;
import katana.customerwalkin.utils.SoftKeyboardLayout;
/**
 * ka
 * 22/08/2017
 */

public abstract class BaseChildFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final Logger LOGGER = Logger.getLogger(BaseFragment.class);

    protected View rootView;

    public abstract int getLayoutId();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onGlobalLayout() {
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(layoutId, container, false);
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            ButterKnife.bind(this, rootView);
        } catch (InflateException e) {
            LOGGER.error(e.getMessage());
        }
//        rootView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
        return rootView;
    }

    public View findViewById(int resId) {
        return rootView.findViewById(resId);
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

    public void setOnTouchListener(final boolean onTouchListener) {
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchListener;
            }
        });
    }

    public void handleBackPress(final OnBackOriginScreenListener onBackOriginScreenListener, final boolean
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
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_close);
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

    public void handleBackPress(final OnBackOriginScreenListenerWithData onBackOriginScreenListener) {
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
                        onBackOriginScreenListener.onBackOriginScreenWithData(null);
                    }
                    return true;
                }
                return false;
            }
        });
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_close);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard();
                    if (onBackOriginScreenListener != null) {
                        onBackOriginScreenListener.onBackOriginScreenWithData(null);
                    }
                }
            });
        }
    }

    public void updateToolbar(String title) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView toolBarTitle = (TextView) getActivity().findViewById(R.id.toolbarTitle);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }
        toolBarTitle.setText(title);
        toolBarTitle.setVisibility(View.VISIBLE);
    }

    public void handleBackPressToHide(final OnBackOriginScreenListener onBackOriginScreenListener, final boolean
            requestUpdate) {
//        if (getView() != null) {
        rootView.setFocusableInTouchMode(true);
//        }
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK || event.getAction()
                        == KeyEvent.ACTION_UP) {
                    // handle back button
                    if (onBackOriginScreenListener != null) {
                        onBackOriginScreenListener.onBackOriginScreen(requestUpdate);
                    }
                    return true;
                }
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
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

    public void handleBackPressToHide(final OnBackOriginScreenListener onBackOriginScreenListener, final boolean
            requestUpdate, View view) {
//        if (getView() != null) {
        view.setFocusableInTouchMode(true);
//        }
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK || event.getAction()
                        == KeyEvent.ACTION_UP) {
                    // handle back button
                    if (onBackOriginScreenListener != null) {
                        onBackOriginScreenListener.onBackOriginScreen(requestUpdate);
                    }
                    return true;
                }
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
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

    public void handleBackPressToHide(final OnBackOriginScreenListenerWithData onBackOriginScreenListener) {
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
                        onBackOriginScreenListener.onBackOriginScreenWithData(null);
                    }
                    return true;
                }
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard();
                    if (onBackOriginScreenListener != null) {
                        onBackOriginScreenListener.onBackOriginScreenWithData(null);
                    }
                }
            });
        }
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
                        onBackOriginScreenListener.onBackOriginScreen(true);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void catchEventKeyboardToHide(SoftKeyboardLayout softKeyboardLayout, final OnBackOriginScreenListener onBackOriginScreenListener) {
        softKeyboardLayout.setOnSoftKeyboardVisibilityChangeListener(
                new SoftKeyboardLayout.SoftKeyboardVisibilityChangeListener() {
                    @Override
                    public void onSoftKeyboardShow() {
                    }

                    @Override
                    public void onSoftKeyboardHide() {
                        handleBackPressToHide(onBackOriginScreenListener, true);
                    }
                });
    }

    public void catchEventKeyboard(SoftKeyboardLayout softKeyboardLayout, final OnBackOriginScreenListener onBackOriginScreenListener) {
        softKeyboardLayout.setOnSoftKeyboardVisibilityChangeListener(
                new SoftKeyboardLayout.SoftKeyboardVisibilityChangeListener() {
                    @Override
                    public void onSoftKeyboardShow() {
                    }

                    @Override
                    public void onSoftKeyboardHide() {
                        handleBackPress(onBackOriginScreenListener, true);
                    }
                });
    }

    public void catchEventKeyboardToHide(SoftKeyboardLayout softKeyboardLayout, final OnBackOriginScreenListenerWithData onBackOriginScreenListener) {
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

    public void hideSoftKeyboard() {
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

    public void setKeyboardFocus(final EditText primaryTextField) {
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 100);
    }

    public void setActionDone(EditText editText, final OnActionDoneListener onActionDoneListener) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onActionDoneListener.onActionDone();
                }
                return false;
            }
        });
    }

    public interface OnActionDoneListener {
        void onActionDone();
    }
}
