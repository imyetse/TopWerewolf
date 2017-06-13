package cn.tse.pr.ui.widget.loading;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.tse.pr.R;


public class ConfirmDialog extends Dialog {
    WindowManager wManager;
    private Button okBtn, cancelBtn;
    private Context ctx;
    private String message;
    private String title;
    private View.OnClickListener okClickListener;
    private View.OnClickListener cancelClickListener;
    private String okBtnTitle = "确定";
    private String cancelBtnTitle = "取消";

    public ConfirmDialog setOkBtn(String content) {
        okBtnTitle = content;
        return this;
    }

    public ConfirmDialog setCancelBtn(String content) {
        cancelBtnTitle = content;
        return this;
    }

    public ConfirmDialog(Context context,
                         String message, View.OnClickListener _okClickListener,
                         View.OnClickListener _cancelClickListener) {
        super(context, R.style.AppTheme_Dialog);
        wManager = ((Activity) context).getWindowManager();
        ctx = context;
        okClickListener = _okClickListener;
        cancelClickListener = _cancelClickListener;
        this.message = message;
        textView = (TextView) findViewById(R.id.tx_confirmdialog_msg);
        this.setCanceledOnTouchOutside(false);
    }

    public ConfirmDialog(Context context,
                         String title, String message,
                         View.OnClickListener _okClickListener,
                         View.OnClickListener _cacelClickListener) {
        super(context, R.style.AppTheme_Dialog);
        wManager = ((Activity) context).getWindowManager();
        ctx = context;
        this.title = title;
        okClickListener = _okClickListener;
        cancelClickListener = _cacelClickListener;
        this.message = message;
        textView = (TextView) findViewById(R.id.tx_confirmdialog_msg);
        this.setCanceledOnTouchOutside(false);
    }


    public void closeDialog() {
        this.dismiss();
    }

    public EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        final View root = inflater.inflate(R.layout.ghost__dialog_confirm,
                (ViewGroup) findViewById(R.id.llyt_confirm_dialog));
        setCanceledOnTouchOutside(false);
        this.setContentView(root);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        Display d = wManager.getDefaultDisplay();
        params.width = (int) (d.getWidth() * 0.8);

        textView = (TextView) findViewById(R.id.tx_confirmdialog_msg);
        if (TextUtils.isEmpty(message)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
        TextView titleView = (TextView) findViewById(R.id.tx_confirmdialog_title);
        textView.setAutoLinkMask(Linkify.WEB_URLS);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(textView, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
        if (TextUtils.isEmpty(title)) {
            titleView.setVisibility(View.GONE);
        }
        titleView.setText(title);
        getWindow().setAttributes(params);
        okBtn = (Button) findViewById(R.id.btn_confirm_dialog_ok);
        cancelBtn = (Button) findViewById(R.id.btn_confirm_dialog_cancel);
        textView.setText(this.message);
        if (okClickListener == null) {
            cancelBtn.setVisibility(View.GONE);
        }
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
                if (okClickListener != null)
                    okClickListener.onClick(v);
            }
        });
        setCancelable(true);
        okBtn.setText(okBtnTitle);
        cancelBtn.setText(cancelBtnTitle);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
                if (cancelClickListener != null)
                    cancelClickListener.onClick(v);
            }
        });
        setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                return false;
            }
        });
        setOnCancelListener(new OnCancelListener() {

            public void onCancel(DialogInterface dialog) {
                closeDialog();
            }
        });
    }
}
