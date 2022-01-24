package com.grzeluu.plantcareapp.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.grzeluu.plantcareapp.utils.ProgressDialogUtils;

public abstract class BaseFragment extends Fragment implements BaseViewContract {
    ProgressDialog progressDialog;
    private BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLoading() {
        hideLoading();
        progressDialog = ProgressDialogUtils.showLoadingDialog(this.getContext());
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void showMessage(String message) {
        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

}
