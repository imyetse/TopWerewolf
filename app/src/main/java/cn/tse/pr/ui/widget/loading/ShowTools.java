package cn.tse.pr.ui.widget.loading;

import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ShowTools {
    private final int UnCancelableRequest = 0x00;

    LoadingDialog loadingDialog;
    AlertDialog alertDialog;
    ConfirmDialog confirmDialog;

    /**
     * 打开加载中的框
     *
     * @param requestCode
     * @param msg
     * @return
     */
    public LoadingDialog showLoading(int requestCode, String msg) {
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog(context);
        if (requestCode == UnCancelableRequest) {
            //某些请求不能取消
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.setLoadingText(msg);
        loadingDialog.show();
        return loadingDialog;
    }

    public LoadingDialog showLoading(String msg) {
        return showLoading(-1, msg);
    }

    /**
     * 关闭加载中的框
     */
    public void disLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 弹出框确认
     *
     * @return
     */
    public AlertDialog showAlert(String message) {
        alertDialog = new AlertDialog(context, message);
        alertDialog.show();
        return alertDialog;
    }

    /**
     * 关闭确认
     */
    public void disAlert() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    /**
     * 关闭所有的窗口
     */
    public void disAll() {
        disLoading();
        disAlert();
        disConfirm();
    }

    /**
     * @param message
     * @param okClickListener    确定按钮的操作
     * @param cacelClickListener 取消按钮的操做
     * @return
     */
    public ConfirmDialog showConfirm(String message,
                                     View.OnClickListener okClickListener,
                                     View.OnClickListener cacelClickListener) {
        confirmDialog = new ConfirmDialog(context,
                message,
                okClickListener, cacelClickListener);
        confirmDialog.show();
        return confirmDialog;
    }

    /**
     * 关闭确认
     */
    public void disConfirm() {
        if (confirmDialog != null) {
            confirmDialog.dismiss();
        }
    }


    /**
     * 操作延迟
     */
    private static final int delayTime = 700;

    Context context;

    public ShowTools(Context _conContext) {
        this.context = _conContext;
    }

    /**
     * toast显示,LENGTH_SHORT
     *
     * @param msg
     */
    public void showToast(final String msg) {

        if (msg != null && msg.trim().length() > 0)
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Looper.prepare();
                    if (context != null) {
                        //有时会出现content为null的情况  估计是网络请求回调 Activity 已经destroy的情况
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }
            }).start();
    }

    /**
     * toast显示 Long
     *
     * @param msg
     */
    public void showToastLong(final String msg) {
        if (msg != null && msg.length() > 0)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }).start();
    }

}
