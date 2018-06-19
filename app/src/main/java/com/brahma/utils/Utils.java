package com.brahma.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.brahma.R;

public class Utils {
    public static ProgressDialog mProgressDialog;
    /**
     * Show loader dialog on SignUp Screen
     */
    public static void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    /**
     * hide loader dialog if it exists
     */
    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
