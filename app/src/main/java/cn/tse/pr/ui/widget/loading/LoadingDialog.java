package cn.tse.pr.ui.widget.loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import cn.tse.pr.R;


public class LoadingDialog {


    private Context mContext;
    private Dialog mDialog;
    private ImageView mLoadingView;
    private View mDialogContentView;


    public LoadingDialog(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.AppTheme_Dialog);
        mDialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);


        mLoadingView = (ImageView) mDialogContentView.findViewById(R.id.lading_view);
        mDialog.setContentView(mDialogContentView);
    }

    public void setBackground(int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) mDialogContentView.getBackground();
        gradientDrawable.setColor(color);
    }

    public void setLoadingText(CharSequence charSequence) {
        //   mLoadingView.setLoadingText(charSequence);
    }

    public void show() {
        try {
            final AnimationDrawable animationDrawable = (AnimationDrawable) mLoadingView
                    .getBackground();
            mLoadingView.post(new Runnable() {
                @Override
                public void run() {
                    animationDrawable.start();
                }
            });
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        } catch (Exception ex) {

        }
    }

    public void dismiss() {
        try {
            if (mDialog != null)
                mDialog.dismiss();
        } catch (Exception ex) {

        }
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        mDialog.setCancelable(cancel);
    }

}
